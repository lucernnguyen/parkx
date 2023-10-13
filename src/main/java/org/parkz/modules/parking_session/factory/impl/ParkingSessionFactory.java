package org.parkz.modules.parking_session.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.parkz.modules.parking_session.enums.ParkingSessionErrorCode;
import org.parkz.modules.parking_session.factory.IParkingSessionFactory;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.repository.ParkingSessionRepository;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class ParkingSessionFactory
        extends BasePersistDataFactory<UUID, ParkingSessionInfo, ParkingSessionInfo, UUID, ParkingSessionEntity, ParkingSessionRepository>
        implements IParkingSessionFactory {

    protected ParkingSessionEntity findByIdNotNull(UUID id) throws InvalidException {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidException(notFound()));
    }

    @Override
    protected IErrorCode notFound() {
        return ParkingSessionErrorCode.PARKING_SESSION_NOT_FOUND;
    }

    @Override
    protected IErrorCode conflict() {
        return ParkingSessionErrorCode.PARKING_SESSION_DUPLICATE_VEHICLE_CHECKIN;
    }
}
