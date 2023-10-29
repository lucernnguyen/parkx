package org.parkz.modules.parking_session.factory.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parkz.modules.parking.enums.ParkingSlotErrorCode;
import org.parkz.modules.parking.factory.IParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.parkz.modules.parking_session.entity.redis.ParkingSessionRedisEntity;
import org.parkz.modules.parking_session.enums.ParkingSessionStatus;
import org.parkz.modules.parking_session.enums.PaymentType;
import org.parkz.modules.parking_session.factory.impl.ParkingSessionFactory;
import org.parkz.modules.parking_session.mapper.ParkingSessionMapper;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.parking_session.model.request.InitCheckoutRequest;
import org.parkz.modules.vehicle.factory.IVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.shared.event.parking_session.InitCheckOutEvent;
import org.parkz.shared.event.parking_session.PaymentBeforeCheckOutEvent;
import org.parkz.shared.event.parking_session.VehicleCheckInEvent;
import org.parkz.shared.event.parking_session.VehicleCheckOutEvent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.filter.IFilter;
import org.springframework.fastboot.rest.common.mapper.BaseMapper;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
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

    @Override
    @Transactional
    public ParkingSessionInfo checkin(CreateParkingSessionRequest request) throws InvalidException {
        log.info("[PARKING_SESSION] Start check in flow with request: {}", request);
        if (repository.existsByVehicleIdAndCheckOutTimeIsNull(request.getVehicleId())) {
            log.warn("[PARKING_SESSION] Vehicle {} already checked in", request.getVehicleId());
            throw new InvalidException(conflict());
        }
        VehicleInfo vehicleInfo = vehicleFactory.getDetailModel(request.getVehicleId(), null);
        ParkingSlotInfo parkingSlotInfo = parkingSlotFactory.getParkingSlotNullable(request.getParkingSlotId());
        if (parkingSlotInfo != null && parkingSlotInfo.isHasParking()) {
            log.warn("[PARKING_SESSION] Parking slot {} already used", request.getParkingSlotId());
            throw new InvalidException(ParkingSlotErrorCode.PARKING_SLOT_POSITION_ALREADY_USED);
        }
        ParkingSessionRedisEntity parkingSessionRedis = parkingSessionMapper.createConvertToRedisEntity(request, vehicleInfo, parkingSlotInfo);
        parkingSessionRedis = parkingSessionRedisRepository.save(parkingSessionRedis);
        log.info("[PARKING_SESSION] Init check in for vehicle {} successfully", request.getVehicleId());
        return parkingSessionMapper.convertToDetail(parkingSessionRedis);
    }

    @Override
    protected <F extends IFilter> ParkingSessionInfo aroundGetDetail(UUID id, F filter) throws InvalidException {
        ParkingSessionRedisEntity parkingSession = parkingSessionRedisRepository.findById(id)
                .orElse(null);
        if (parkingSession != null) {
            return parkingSessionMapper.convertToDetail(parkingSession);
        }
        return super.aroundGetDetail(id, filter);
    }

    @Override
    public SuccessResponse confirmCheckIn(ConfirmCheckInRequest request) throws InvalidException {
        log.info("[PARKING_SESSION] Start confirm check in with request: {}", request);
        ParkingSessionRedisEntity parkingSessionRedis = parkingSessionRedisRepository.findById(request.getSessionId())
                .orElseThrow(() -> new InvalidException(notFound()));
        ParkingSessionEntity parkingSession = repository.save(
                parkingSessionMapper.createConvertToEntity(parkingSessionRedis)
                        .setConfirmed(true)
                        .setCheckInCapture(request.getCheckInCapture())
                        .setCheckInTime(LocalDateTime.now())
        );
        applicationEventPublisher.publishEvent(new VehicleCheckInEvent(parkingSession.getVehicleId(), parkingSession.getParkingSlotId()));
        parkingSessionRedisRepository.deleteById(parkingSessionRedis.getId());
        log.info("[PARKING_SESSION] Confirm check in with sessionId: {}", parkingSession.getId());
        return SuccessResponse.status(true);
    }

    @Override
    @Transactional
    public SuccessResponse initCheckout(InitCheckoutRequest request) throws InvalidException {
        log.info("[PARKING_SESSION] Start check out flow with request: {}", request);
        ParkingSessionEntity parkingSession = findByIdNotNull(request.getSessionId());
        if (PaymentType.E_WALLET.equals(request.getPaymentType())) {
            log.info("[PARKING_SESSION] User init payment with type {}", request.getPaymentType());
            applicationEventPublisher.publishEvent(new InitCheckOutEvent(
                    JwtUtils.getUserIdString(),
                    parkingSession.getId(),
                    Objects.requireNonNullElse(parkingSession.getVehicleSnapShot().getVehicleTypePrice(), new BigDecimal("0.0"))

            ));
        }
        parkingSession
                .setPaymentType(request.getPaymentType())
                .setStatus(ParkingSessionStatus.WAITING_PAYMENT);
        repository.save(parkingSession);
        log.info("[PARKING_SESSION] Init check out for sessionId {} successfully", request.getSessionId());
        return SuccessResponse.status(true);
    }

    @Override
    @Transactional
    public SuccessResponse checkOut(ConfirmCheckOutRequest request) throws InvalidException {
        log.info("[PARKING_SESSION] Confirm checkout with request: {}", request);
        ParkingSessionEntity parkingSession = findByIdNotNull(request.getSessionId());
        if (PaymentType.E_WALLET.equals(parkingSession.getPaymentType())) {
            applicationEventPublisher.publishEvent(new PaymentBeforeCheckOutEvent(parkingSession.getId()));
        }
        parkingSession
                .setCheckOutTime(LocalDateTime.now())
                .setStatus(ParkingSessionStatus.CHECKED_OUT)
                .setCheckOutCapture(request.getCheckOutCapture());
        repository.save(parkingSession);
        applicationEventPublisher.publishEvent(new VehicleCheckOutEvent(parkingSession.getVehicleId(), parkingSession.getParkingSlotId()));
        log.info("[PARKING_SESSION] Confirm checkout for sessionId {} successfully", request.getSessionId());
        return SuccessResponse.status(true);
    }

    @Override
    protected BaseMapper<ParkingSessionInfo, ParkingSessionInfo, ParkingSessionEntity> getMapper() {
        return parkingSessionMapper;
    }
}
