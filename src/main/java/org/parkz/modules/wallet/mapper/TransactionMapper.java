package org.parkz.modules.wallet.mapper;

import org.mapstruct.*;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class TransactionMapper {

    @Mapping(target = "id", ignore = true)
    public abstract TransactionEntity transactionEntity(TransactionRedisEntity transactionRedis);
}
