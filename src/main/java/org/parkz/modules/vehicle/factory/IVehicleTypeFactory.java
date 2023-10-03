package org.parkz.modules.vehicle.factory;

import org.parkz.modules.vehicle.model.VehicleTypeDetails;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

public interface IVehicleTypeFactory extends IDataFactory<Integer, VehicleTypeInfo, VehicleTypeDetails> {
}
