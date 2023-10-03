package org.parkz.modules.vehicle.mapper;

import org.mapstruct.*;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VehicleMapper extends BaseMapper<VehicleInfo, VehicleInfo, VehicleEntity> {

    @Override
    @Mapping(target = "user", ignore = true)
    VehicleEntity createConvertToEntity(VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "user", ignore = true)
    void updateConvertToEntity(@MappingTarget VehicleEntity entity, VehicleInfo detail) throws InvalidException;

    @Override
    default VehicleInfo convertToDetail(VehicleEntity entity) throws InvalidException {
        return convertToInfo(entity);
    }

    @Override
    @Mapping(target = "userId", source = "user.id")
    VehicleInfo convertToInfo(VehicleEntity entity) throws InvalidException;
}
