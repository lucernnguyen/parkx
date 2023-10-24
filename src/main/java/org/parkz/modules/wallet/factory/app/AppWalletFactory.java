package org.parkz.modules.wallet.factory.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.cache.domain.Currency;
import org.parkz.cache.factory.CurrencyFactory;
import org.parkz.infrastructure.client.paypal.PayPalClient;
import org.parkz.infrastructure.client.paypal.common.*;
import org.parkz.infrastructure.client.paypal.request.CreateOrderRequest;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.parkz.modules.wallet.enums.TransactionErrorCode;
import org.parkz.modules.wallet.enums.TransactionType;
import org.parkz.modules.wallet.enums.WalletErrorCode;
import org.parkz.modules.wallet.factory.ITransactionFactory;
import org.parkz.modules.wallet.factory.impl.WalletFactory;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.DepositResponse;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.parkz.shared.enums.CurrencyCode;
import org.parkz.shared.event.parking_session.InitCheckOutEvent;
import org.parkz.shared.event.parking_session.PaymentBeforeCheckOutEvent;
import org.parkz.shared.event.user.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppWalletFactory extends WalletFactory implements IAppWalletFactory {

    private final PayPalClient payPalClient;
    private final CurrencyFactory currencyFactory;
    @Qualifier("transactionFactory")
    private final ITransactionFactory transactionFactory;
    @Value("${paypal.deposit-url}")
    private final String depositUrl;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, classes = {UserCreatedEvent.class})
    public void userCreatedListener(UserCreatedEvent event) {
        WalletEntity wallet = WalletEntity.builder()
                .userId(event.userId())
                .build();
        repository.save(wallet);
    }

    @Override
    public WalletInfo getDetailByContext() throws InvalidException {
        return convertToDetail(getWalletByContext());
    }

    @Override
    @Transactional
    public InquiryResponse inquiry(InquiryRequest request) throws InvalidException {
        log.info("[APP_WALLET] Init inquiry proceed with request: {}", request);
        TransactionRedisEntity transaction = transactionFactory.createTransactionRedis(
                JwtUtils.getUserIdString(),
                request.getAmount(),
                TransactionType.DEPOSIT
        );
        Currency currency = currencyFactory.getByCode(CurrencyCode.getDefault());
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(depositUrl)
                .queryParam("transactionId", transaction.getId())
                .build();
        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                .intent(OrderIntent.CAPTURE)
                .purchaseUnit(PurchaseUnit.builder()
                        .item(Item.builder()
                                .name("Nap tien Parkx")
                                .quantity(1)
                                .description("Nap tien Parkx")
                                .unitAmount(currency.convert(new Money(request.getAmount().doubleValue(), CurrencyCode.VND), CurrencyCode.getDefault()))
                                .build()
                        )
                        .referenceId(transaction.getId().toString())
                        .description("Nap tien Parkx")
                        .customId(transaction.getId().toString())
                        .amount(Amount.builder()
                                .currencyCode(CurrencyCode.getDefault())
                                .breakdown(Amount.Breakdown.builder()
                                        .itemTotal(currency.convert(new Money(request.getAmount().doubleValue(), CurrencyCode.VND), CurrencyCode.getDefault()))
                                        .shipping(new Money(0.0, CurrencyCode.getDefault()))
                                        .discount(new Money(0.0, CurrencyCode.getDefault()))
                                        .build()
                                )
                                .build()
                        )
                        .build()
                )
                .applicationContext(PayPalAppContext.builder()
                        .brandName("Parkx")
                        .returnUrl(uriComponents.toString())
                        .landingPage(PaymentLandingPage.BILLING)
                        .build()
                )
                .build();
        log.info("[APP_WALLET] Creating PayPal Order Url with request");
        CreateOrderResponse orderResponse = payPalClient.createOrder(orderRequest);
        transactionFactory.setTransactionRedisRefId(transaction.getId(), orderResponse.getId());
        String approveLink = orderResponse.getApproveLink();
        log.info("[APP_WALLET] Inquiry successfully with PayPal Approve Link: {}", approveLink);
        return InquiryResponse.builder()
                .redirectUrl(approveLink)
                .build();
    }

    @Override
    @Transactional
    public DepositResponse deposit(DepositRequest request) throws InvalidException {
        log.info("[APP_WALLET] Start deposit proceed with PayPal, request body: {}", request);
        TransactionRedisEntity transactionRedis = transactionFactory.findTransactionRedisByIdNotNull(request.getTransactionId());
        log.info("[APP_WALLET] Transaction data from redis: {}", transactionRedis);
        WalletEntity wallet = findWalletByUserIdNotNull(transactionRedis.getUserId());
        CreateOrderResponse orderResponse = payPalClient.captureOrder(transactionRedis.getRefTransactionId());
        if (orderResponse != null && OrderStatus.COMPLETED.equals(orderResponse.getStatus())) {
            log.info("[APP_WALLET] Capture order from PayPal successfully with response: {}", orderResponse);
            UUID transactionId = transactionFactory.deposit(transactionRedis, orderResponse);
            wallet.addBalance(transactionRedis.getAmount());
            repository.save(wallet);
            return DepositResponse.builder()
                    .transactionId(transactionId)
                    .build();
        }
        log.warn("[APP_WALLET] Capture order from PayPal failed");
        throw new InvalidException(WalletErrorCode.WALLET_DEPOSIT_CAPTURE_PAYPAL_FAILED);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = InitCheckOutEvent.class)
    protected void initCheckOutListener(InitCheckOutEvent event) throws InvalidException {
        log.info("[APP_WALLET] Init check out with event data: {}", event);
        WalletEntity wallet = findWalletByUserIdNotNull(event.userId());
        if (!wallet.checkBalance(event.vehicleTypePrice())) {
            log.warn("[APP_WALLET] Wallet of userId {} not enough", event.userId());
            throw new InvalidException(TransactionErrorCode.TRANSACTION_BALANCE_NOT_ENOUGH);
        }
        TransactionRedisEntity transactionRedis = transactionFactory.createTransactionRedis(
                event.userId(),
                event.vehicleTypePrice(),
                TransactionType.PAYMENT
        );
        transactionFactory.setTransactionRedisRefId(transactionRedis.getId(), event.parkingSessionId().toString());
        log.info("[APP_WALLET]: Init check out successfully with transaction data: {}", transactionRedis);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = PaymentBeforeCheckOutEvent.class)
    protected void paymentBeforeCheckOutListener(PaymentBeforeCheckOutEvent event) throws InvalidException {
        log.info("[APP_WALLET]: Init payment before check out with event data: {}", event);
        TransactionRedisEntity transactionRedis = transactionFactory.findTransactionRedisByRefIdNotNull(event.parkingSessionId().toString());
        WalletEntity wallet = findWalletByUserIdNotNull(transactionRedis.getUserId());
        if (!wallet.checkBalance(transactionRedis.getAmount())) {
            log.warn("[APP_WALLET] Wallet of userId {} not enough", wallet.getUserId());
            throw new InvalidException(TransactionErrorCode.TRANSACTION_BALANCE_NOT_ENOUGH);
        }
        transactionFactory.deposit(transactionRedis, null);
        wallet.subtractBalance(transactionRedis.getAmount());
        repository.save(wallet);
        log.info("[APP_WALLET]: Payment successfully for parking sessionId: {}", event.parkingSessionId());
    }

    private WalletEntity getWalletByContext() throws InvalidException {
        return repository.findByUserId(JwtUtils.getUserIdString())
                .orElseThrow(() -> new InvalidException(notFound()));
    }
}
