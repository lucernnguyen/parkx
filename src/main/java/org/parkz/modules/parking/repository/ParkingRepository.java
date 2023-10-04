package org.parkz.modules.parking.repository;

import jakarta.persistence.QueryHint;
import lombok.NonNull;
import org.parkz.modules.parking.entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingEntity, UUID>, JpaSpecificationExecutor<ParkingEntity> {

    @Override
    @NonNull
    @QueryHints(value = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<ParkingEntity> findAll();
}
