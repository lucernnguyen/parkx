package org.parkz.modules.vehicle.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;
import org.springframework.fastboot.security.utils.JwtUtils;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {JwtUtils.class},
        uses = {VehicleTypeMapper.class}
)
@Setter(onMethod_ = {@Autowired})
public abstract class VehicleMapper implements BaseMapper<VehicleInfo, VehicleInfo, VehicleEntity> {

    @Override
    @Mapping(target = "userId", expression = "java(JwtUtils.getUserIdString())")
    @Mapping(target = "vehicleType", source = "vehicleTypeId", qualifiedByName = "fromVehicleTypeIdToEntityMapper")
    public abstract VehicleEntity createConvertToEntity(VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "vehicleType", source = "vehicleTypeId", qualifiedByName = "fromVehicleTypeIdToEntityMapper")
    public abstract void updateConvertToEntity(@MappingTarget VehicleEntity entity, VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "vehicleTypeId", source = "vehicleType.id")
    public abstract VehicleInfo convertToInfo(VehicleEntity entity) throws InvalidException;

}
