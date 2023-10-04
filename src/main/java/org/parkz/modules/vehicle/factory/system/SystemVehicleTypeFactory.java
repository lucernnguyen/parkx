package org.parkz.modules.vehicle.factory.system;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.factory.impl.VehicleTypeFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemVehicleTypeFactory extends VehicleTypeFactory implements ISystemVehicleTypeFactory {

}
