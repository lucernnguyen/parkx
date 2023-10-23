package org.parkz.modules.vehicle.mapper;

import lombok.Setter;
import org.mapstruct.*;
import org.parkz.modules.parking_session.mapper.ParkingSessionMapper;
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
        uses = {VehicleTypeMapper.class, ParkingSessionMapper.class}
)
@Setter(onMethod_ = {@Autowired})
public abstract class VehicleMapper implements BaseMapper<VehicleInfo, VehicleInfo, VehicleEntity> {

    @Override
    @Mapping(target = "userId", expression = "java(JwtUtils.getUserIdString())")
    @Mapping(target = "parkingSessionActive", ignore = true)
    public abstract VehicleEntity createConvertToEntity(VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "parkingSessionActive", ignore = true)
    public abstract void updateConvertToEntity(@MappingTarget VehicleEntity entity, VehicleInfo detail) throws InvalidException;

    @Override
    @Mapping(target = "parkingSessionActive", source = "parkingSessionActive", qualifiedByName = "convertToInfoMapper")
    public abstract VehicleInfo convertToInfo(VehicleEntity entity) throws InvalidException;

    @Override
    @Mapping(target = "vehicleTypeName", source = "vehicleType.name")
    @Mapping(target = "vehicleTypePrice", source = "vehicleType.price")
    @Mapping(target = "parkingSessionActive", source = "parkingSessionActive", qualifiedByName = "convertToDetailMapper")
    public abstract VehicleInfo convertToDetail(VehicleEntity entity) throws InvalidException;


}
