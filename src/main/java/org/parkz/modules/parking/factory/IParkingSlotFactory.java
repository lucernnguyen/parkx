package org.parkz.modules.parking.factory;

import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IParkingSlotFactory extends IDataFactory<UUID, ParkingSlotInfo, ParkingSlotInfo> {
}
