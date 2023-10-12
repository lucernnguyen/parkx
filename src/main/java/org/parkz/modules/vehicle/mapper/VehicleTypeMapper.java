package org.parkz.modules.vehicle.mapper;

import org.mapstruct.*;
import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.parkz.modules.vehicle.model.VehicleTypeDetails;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class VehicleTypeMapper implements BaseMapper<VehicleTypeInfo, VehicleTypeDetails, VehicleTypeEntity> {

}
