package org.parkz.modules.wallet.mapper;

import org.mapstruct.*;
import org.parkz.modules.wallet.entity.WalletEntity;
import org.parkz.modules.wallet.model.WalletInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class WalletMapper implements BaseMapper<WalletInfo, WalletInfo, WalletEntity> {

    @Override
    public WalletEntity createConvertToEntity(WalletInfo detail) throws InvalidException {
        throw new UnsupportedOperationException();
    }
}
