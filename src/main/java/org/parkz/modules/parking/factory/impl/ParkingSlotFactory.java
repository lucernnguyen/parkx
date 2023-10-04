package org.parkz.modules.parking.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.factory.IParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking.repository.ParkingSlotRepository;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class ParkingSlotFactory
        extends BasePersistDataFactory<UUID, ParkingSlotInfo, ParkingSlotInfo, UUID, ParkingSlotEntity, ParkingSlotRepository>
        implements IParkingSlotFactory {

}
