package org.parkz.modules.parking_session.mapper;

import org.mapstruct.*;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {LocalDateTime.class}
)
public abstract class ParkingSessionMapper implements BaseMapper<ParkingSessionInfo, ParkingSessionInfo, ParkingSessionEntity> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    public abstract ParkingSessionEntity createConvertToEntity(ParkingSessionInfo detail) throws InvalidException;

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "vehicleId", source = "request.vehicleId")
    @Mapping(target = "parkingSlotId", source = "request.parkingSlotId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "vehicleSnapShot", source = "vehicleInfo")
    @Mapping(target = "parkingSlotSnapShot", source = "parkingSlotInfo")
    public abstract ParkingSessionEntity createConvertToEntity(CreateParkingSessionRequest request, VehicleInfo vehicleInfo, ParkingSlotInfo parkingSlotInfo);

    @Override
    @BeanMapping(ignoreByDefault = true)
    public abstract void updateConvertToEntity(@MappingTarget ParkingSessionEntity entity, ParkingSessionInfo detail) throws InvalidException;
}
