package org.parkz.modules.parking.factory;

import org.parkz.modules.parking.model.ParkingInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IParkingFactory extends IDataFactory<UUID, ParkingInfo, ParkingInfo> {
}
