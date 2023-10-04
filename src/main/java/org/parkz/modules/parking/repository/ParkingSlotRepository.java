package org.parkz.modules.parking.repository;

import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlotEntity, UUID>, JpaSpecificationExecutor<ParkingSlotEntity> {

    boolean existsByRowIndexAndColumnIndex(Integer rowIndex, Integer columnIndex);
    boolean existsByRowIndexAndColumnIndexAndIdNot(Integer rowIndex, Integer columnIndex, UUID id);
}
