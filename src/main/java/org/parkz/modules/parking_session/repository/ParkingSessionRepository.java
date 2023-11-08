package org.parkz.modules.parking_session.repository;

import org.parkz.modules.parking_session.entity.ParkingSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSessionEntity, UUID>, JpaSpecificationExecutor<ParkingSessionEntity> {

    boolean existsByVehicleIdAndCheckOutTimeIsNull(UUID vehicleId);

    @Query(value = """
            SELECT COUNT(ps.id)
            FROM parkx_parking_session ps
            WHERE
                CAST(ps.vehicle_snap_shot -> 'vehicleTypeId' AS INT) = :vehicleTypeId
                AND ps.check_in_time IS NOT NULL
                AND PS.check_out_time IS NULL""",
            nativeQuery = true)
    int countByVehicleTypeId(@Param("vehicleTypeId") Integer vehicleTypeId);
}
