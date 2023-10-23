package org.parkz.modules.wallet.factory;

import org.parkz.infrastructure.client.paypal.response.CreateOrderResponse;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.parkz.modules.wallet.enums.TransactionType;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.math.BigDecimal;
import java.util.UUID;

public interface ITransactionFactory extends IDataFactory<UUID, TransactionInfo, TransactionDetails> {

    TransactionRedisEntity createTransactionRedis(String userId, BigDecimal amount, TransactionType transactionType) throws InvalidException;

    void setTransactionRedisRefId(UUID id, String refId) throws InvalidException;

    void deposit(TransactionRedisEntity transactionRedis, CreateOrderResponse orderResponse) throws InvalidException;

    TransactionRedisEntity findTransactionRedisByIdNotNull(UUID id) throws InvalidException;

    TransactionRedisEntity findTransactionRedisByRefIdNotNull(String id) throws InvalidException;

}
