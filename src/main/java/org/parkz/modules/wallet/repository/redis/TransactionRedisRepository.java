package org.parkz.modules.wallet.repository.redis;

import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRedisRepository extends KeyValueRepository<TransactionRedisEntity, UUID> {
}
