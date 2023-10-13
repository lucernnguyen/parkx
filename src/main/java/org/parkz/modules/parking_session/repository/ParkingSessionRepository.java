package org.parkz.modules.parking_session.repository;

import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSessionEntity, UUID>, JpaSpecificationExecutor<ParkingSessionEntity> {

    boolean existsByVehicleIdAndCheckOutTimeIsNull(UUID vehicleId);
}
