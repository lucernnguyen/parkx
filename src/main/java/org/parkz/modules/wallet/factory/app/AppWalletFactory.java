package org.parkz.modules.wallet.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.cache.domain.Currency;
import org.parkz.cache.factory.CurrencyFactory;
import org.parkz.infrastructure.client.paypal.PayPalClient;
import org.parkz.infrastructure.client.paypal.common.*;
import org.parkz.infrastructure.client.paypal.request.CreateOrderRequest;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.parkz.modules.wallet.enums.TransactionErrorCode;
import org.parkz.modules.wallet.enums.TransactionStatus;
import org.parkz.modules.wallet.factory.impl.WalletFactory;
import org.parkz.modules.wallet.mapper.TransactionMapper;
import org.parkz.modules.wallet.model.WalletInfo;
import org.parkz.modules.wallet.model.request.DepositRequest;
import org.parkz.modules.wallet.model.request.InquiryRequest;
import org.parkz.modules.wallet.model.response.InquiryResponse;
import org.parkz.modules.wallet.repository.TransactionRepository;
import org.parkz.modules.wallet.repository.redis.TransactionRedisRepository;
import org.parkz.shared.enums.CurrencyCode;
import org.parkz.shared.event.user.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AppWalletFactory extends WalletFactory implements IAppWalletFactory {

    private final TransactionRepository transactionRepository;
    private final TransactionRedisRepository transactionRedisRepository;
    private final PayPalClient payPalClient;
    private final CurrencyFactory currencyFactory;
    private final TransactionMapper transactionMapper;
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
        WalletEntity wallet = getWalletByContext();
        TransactionRedisEntity transaction = transactionRedisRepository.save(TransactionRedisEntity.builder()
                .balance(wallet.getBalance())
                .amount(request.getAmount())
                .status(TransactionStatus.PENDING)
                .walletId(wallet.getId())
                .userId(JwtUtils.getUserIdString())
                .build()
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
        CreateOrderResponse orderResponse = payPalClient.createOrder(orderRequest);
        transaction.setRefTransactionId(orderResponse.getId());
        transactionRedisRepository.save(transaction);
        return InquiryResponse.builder()
                .redirectUrl(orderResponse.getApproveLink())
                .build();
    }

    @Override
    @Transactional
    public SuccessResponse deposit(DepositRequest request) throws InvalidException {
        TransactionRedisEntity transactionRedis = transactionRedisRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new InvalidException(TransactionErrorCode.TRANSACTION_NOT_FOUND));
        WalletEntity wallet = repository.findByUserId(transactionRedis.getUserId())
                .orElseThrow(() -> new InvalidException(notFound()));
        CreateOrderResponse orderResponse = payPalClient.captureOrder(transactionRedis.getRefTransactionId());
        if (orderResponse != null && OrderStatus.COMPLETED.equals(orderResponse.getStatus())) {
            TransactionEntity transaction = transactionMapper.transactionEntity(transactionRedis)
                    .setStatus(TransactionStatus.SUCCESS)
                    .setOrderData(orderResponse);
            transactionRepository.save(transaction);
            wallet.setBalance(wallet.getBalance().add(transaction.getAmount()));
            repository.save(wallet);
            return SuccessResponse.status(true);
        }
        return SuccessResponse.status(false);
    }

    private WalletEntity getWalletByContext() throws InvalidException {
        return repository.findByUserId(JwtUtils.getUserIdString())
                .orElseThrow(() -> new InvalidException(notFound()));
    }
}
