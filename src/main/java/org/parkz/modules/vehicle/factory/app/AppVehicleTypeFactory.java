package org.parkz.modules.vehicle.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.factory.impl.VehicleTypeFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppVehicleTypeFactory extends VehicleTypeFactory implements IAppVehicleTypeFactory {

}
