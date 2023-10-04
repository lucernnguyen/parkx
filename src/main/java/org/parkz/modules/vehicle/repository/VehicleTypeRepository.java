package org.parkz.modules.vehicle.repository;

import jakarta.persistence.QueryHint;
import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Integer>, JpaSpecificationExecutor<VehicleTypeEntity> {

    @Override
    @NonNull
    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<VehicleTypeEntity> findAll();
}
