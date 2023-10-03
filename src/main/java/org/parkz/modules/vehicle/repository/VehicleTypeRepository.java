package org.parkz.modules.vehicle.repository;

import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Integer>, JpaSpecificationExecutor<VehicleTypeEntity> {
}
