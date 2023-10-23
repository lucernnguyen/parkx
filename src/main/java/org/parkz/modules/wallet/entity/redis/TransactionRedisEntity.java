package org.parkz.modules.wallet.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.parkz.modules.wallet.enums.TransactionStatus;
import org.parkz.modules.wallet.enums.TransactionType;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;

@Data
@Builder
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRedisEntity {

    @Builder.Default
    private UUID id = UUID.randomUUID();
    @TimeToLive
    @Builder.Default
    private long ttl = Duration.ofHours(3).toSeconds();
    private BigDecimal balance;
    private BigDecimal amount;
    private TransactionStatus status;
    private TransactionType transactionType;
    @Indexed
    private String refTransactionId;
    private UUID walletId;
    private String userId;
}
