package org.parkz.modules.parking_session.mapper;

import org.mapstruct.*;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.parkz.modules.parking_session.entity.redis.ParkingSessionRedisEntity;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        imports = {LocalDateTime.class, UUID.class, Duration.class}
)
public abstract class ParkingSessionMapper implements BaseMapper<ParkingSessionInfo, ParkingSessionInfo, ParkingSessionEntity> {

    @Override
    public ParkingSessionEntity createConvertToEntity(ParkingSessionInfo detail) throws InvalidException {
        throw new UnsupportedOperationException();
    }

    @Override
    @BeanMapping(ignoreByDefault = true)
    public abstract void updateConvertToEntity(@MappingTarget ParkingSessionEntity entity, ParkingSessionInfo detail) throws InvalidException;

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "timeToLive", expression = "java(Duration.ofMinutes(2).toSeconds())")
    @Mapping(target = "vehicleId", source = "request.vehicleId")
    @Mapping(target = "parkingSlotId", source = "request.parkingSlotId", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "vehicleSnapShot", source = "vehicleInfo")
    @Mapping(target = "parkingSlotSnapShot", source = "parkingSlotInfo")
    public abstract ParkingSessionRedisEntity createConvertToRedisEntity(CreateParkingSessionRequest request, VehicleInfo vehicleInfo, ParkingSlotInfo parkingSlotInfo);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "guestName", source = "guestName")
    @Mapping(target = "guestPhone", source = "guestPhone")
    @Mapping(target = "vehicleId", source = "vehicleId")
    @Mapping(target = "parkingSlotId", source = "parkingSlotId")
    @Mapping(target = "vehicleSnapShot", source = "vehicleSnapShot")
    @Mapping(target = "parkingSlotSnapShot", source = "parkingSlotSnapShot")
    public abstract ParkingSessionEntity createConvertToEntity(ParkingSessionRedisEntity entity);

    public abstract ParkingSessionInfo convertToDetail(ParkingSessionRedisEntity entity);
}
