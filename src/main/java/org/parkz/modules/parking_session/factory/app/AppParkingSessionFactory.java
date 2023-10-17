package org.parkz.modules.parking_session.factory.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.parking_session.entity.redis.ParkingSessionRedisEntity;
import org.parkz.modules.parking_session.repository.redis.ParkingSessionRedisRepository;
import org.parkz.shared.event.parking_session.VehicleCheckInEvent;
import org.parkz.shared.event.parking_session.VehicleCheckOutEvent;
import org.parkz.modules.parking.factory.IParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.parkz.modules.parking_session.factory.impl.ParkingSessionFactory;
import org.parkz.modules.parking_session.mapper.ParkingSessionMapper;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.vehicle.factory.IVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppParkingSessionFactory extends ParkingSessionFactory implements IAppParkingSessionFactory {

    private final ParkingSessionMapper parkingSessionMapper;
    @Qualifier("vehicleFactory")
    private final IVehicleFactory vehicleFactory;
    @Qualifier("parkingSlotFactory")
    private final IParkingSlotFactory parkingSlotFactory;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ParkingSessionRedisRepository parkingSessionRedisRepository;

    @Override
    @Transactional
    public ParkingSessionInfo checkin(CreateParkingSessionRequest request) throws InvalidException {
        if (repository.existsByVehicleIdAndCheckOutTimeIsNull(request.getVehicleId())) {
            throw new InvalidException(conflict());
        }
        VehicleInfo vehicleInfo = vehicleFactory.getDetailModel(request.getVehicleId(), null);
        ParkingSlotInfo parkingSlotInfo = parkingSlotFactory.getParkingSlotNullable(request.getParkingSlotId());
        ParkingSessionRedisEntity parkingSessionRedis = parkingSessionMapper.createConvertToRedisEntity(request, vehicleInfo, parkingSlotInfo);
        parkingSessionRedis = parkingSessionRedisRepository.save(parkingSessionRedis);
        return parkingSessionMapper.convertToDetail(parkingSessionRedis);
    }

    @Override
    public ParkingSessionInfo getSessionInfo(UUID sessionId) throws InvalidException {
        ParkingSessionRedisEntity parkingSession = parkingSessionRedisRepository.findById(sessionId)
                .orElseThrow(() -> new InvalidException(notFound()));
        return parkingSessionMapper.convertToDetail(parkingSession);
    }

    @Override
    public SuccessResponse confirmCheckIn(ConfirmCheckInRequest request) throws InvalidException {
        ParkingSessionRedisEntity parkingSessionRedis = parkingSessionRedisRepository.findById(request.getSessionId())
                .orElseThrow(() -> new InvalidException(notFound()));
        ParkingSessionEntity parkingSession = parkingSessionMapper.createConvertToEntity(parkingSessionRedis);
        parkingSession.setConfirmed(true);
        parkingSession.setCheckInTime(LocalDateTime.now());
        repository.save(parkingSession);
        applicationEventPublisher.publishEvent(new VehicleCheckInEvent(parkingSession.getVehicleId(), parkingSession.getParkingSlotId()));
        parkingSessionRedisRepository.deleteById(parkingSessionRedis.getId());
        return SuccessResponse.status(true);
    }

    @Override
    public SuccessResponse checkOut(ConfirmCheckOutRequest request) throws InvalidException {
        ParkingSessionEntity parkingSession = findByIdNotNull(request.getSessionId());
        parkingSession.setCheckOutTime(LocalDateTime.now());
        repository.save(parkingSession);
        applicationEventPublisher.publishEvent(new VehicleCheckOutEvent(parkingSession.getVehicleId(), parkingSession.getParkingSlotId()));
        return SuccessResponse.status(true);
    }

    @Override
    protected BaseMapper<ParkingSessionInfo, ParkingSessionInfo, ParkingSessionEntity> getMapper() {
        return parkingSessionMapper;
    }
}
