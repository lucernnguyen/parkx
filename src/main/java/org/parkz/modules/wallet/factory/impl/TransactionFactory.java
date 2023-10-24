package org.parkz.modules.wallet.factory.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.parkz.modules.wallet.enums.TransactionErrorCode;
import org.parkz.modules.wallet.enums.TransactionStatus;
import org.parkz.modules.wallet.enums.TransactionType;
import org.parkz.modules.wallet.factory.ITransactionFactory;
import org.parkz.modules.wallet.factory.IWalletFactory;
import org.parkz.modules.wallet.mapper.TransactionMapper;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.parkz.modules.wallet.repository.TransactionRepository;
import org.parkz.modules.wallet.repository.redis.TransactionRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@Setter(value = AccessLevel.PROTECTED, onMethod_ = {@Autowired})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionFactory
        extends BasePersistDataFactory<UUID, TransactionInfo, TransactionDetails, UUID, TransactionEntity, TransactionRepository>
        implements ITransactionFactory {

    protected TransactionRedisRepository transactionRedisRepository;
    protected TransactionMapper transactionMapper;
    @Qualifier("walletFactory")
    protected IWalletFactory walletFactory;

    @Override
    public TransactionRedisEntity createTransactionRedis(String userId, BigDecimal amount, TransactionType transactionType) throws InvalidException {
        WalletEntity wallet = walletFactory.findWalletByUserIdNotNull(userId);
        return transactionRedisRepository.save(TransactionRedisEntity.builder()
                .balance(wallet.getBalance())
                .amount(amount)
                .status(TransactionStatus.PENDING)
                .transactionType(transactionType)
                .walletId(wallet.getId())
                .userId(JwtUtils.getUserIdString())
                .build()
        );
    }

    @Override
    public void setTransactionRedisRefId(UUID id, String refId) throws InvalidException {
        TransactionRedisEntity transactionRedis = findTransactionRedisByIdNotNull(id);
        transactionRedis.setRefTransactionId(refId);
        transactionRedisRepository.save(transactionRedis);
    }

    @Override
    public UUID deposit(TransactionRedisEntity transactionRedis, CreateOrderResponse orderResponse) throws InvalidException {
        log.info("[TRANSACTION] Start deposit with data: {}", transactionRedis);
        if (orderResponse != null) {
            log.info("[TRANSACTION] Order data: {}", orderResponse);
        }
        TransactionEntity transaction = repository.save(
                transactionMapper.toTransactionEntity(transactionRedis)
                        .setStatus(TransactionStatus.SUCCESS)
                        .setOrderData(orderResponse)
        );
        transactionRedisRepository.delete(transactionRedis);
        log.info("[TRANSACTION] Deposit successfully with transactionId: {}", transaction.getId());
        return transaction.getId();
    }

    public TransactionRedisEntity findTransactionRedisByIdNotNull(UUID id) throws InvalidException {
        return transactionRedisRepository.findById(id)
                .orElseThrow(() -> new InvalidException(notFound()));
    }

    public TransactionRedisEntity findTransactionRedisByRefIdNotNull(String id) throws InvalidException {
        return transactionRedisRepository.findByRefTransactionId(id)
                .orElseThrow(() -> new InvalidException(notFound()));
    }

    @Override
    protected IErrorCode notFound() {
        return TransactionErrorCode.TRANSACTION_NOT_FOUND;
    }

}
