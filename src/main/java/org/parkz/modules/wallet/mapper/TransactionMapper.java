package org.parkz.modules.wallet.mapper;

import org.mapstruct.*;
import org.parkz.modules.wallet.entity.TransactionEntity;
import org.parkz.modules.wallet.entity.redis.TransactionRedisEntity;
import org.parkz.modules.wallet.model.TransactionDetails;
import org.parkz.modules.wallet.model.TransactionInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class TransactionMapper implements BaseMapper<TransactionInfo, TransactionDetails, TransactionEntity> {

    @Mapping(target = "id", ignore = true)
    public abstract TransactionEntity toTransactionEntity(TransactionRedisEntity transactionRedis);

    @Override
    public TransactionEntity createConvertToEntity(TransactionDetails detail) throws InvalidException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateConvertToEntity(TransactionEntity entity, TransactionDetails detail) throws InvalidException {
        throw new UnsupportedOperationException();
    }

    @Override
    @InheritConfiguration(name = "convertToInfo")
    @Named("convertToDetailMapper")
    @Mapping(target = "userEmail", source = "user.email")
    public abstract TransactionDetails convertToDetail(TransactionEntity entity) throws InvalidException;
}
