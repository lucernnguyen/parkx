package org.parkz.modules.vehicle.repository;

import jakarta.persistence.QueryHint;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, UUID>, JpaSpecificationExecutor<VehicleEntity> {

    boolean existsByLicensePlate(String licensePlate);

    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<VehicleEntity> findByUserId(String userId);
}
