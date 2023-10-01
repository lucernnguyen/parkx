package org.parkz.modules.vehicle.factory;

import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IVehicleFactory extends IDataFactory<UUID, VehicleInfo, VehicleInfo> {
}
