package org.parkz.modules.vehicle.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.controller.ISystemVehicleTypeController;
import org.parkz.modules.vehicle.factory.system.ISystemVehicleTypeFactory;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemVehicleTypeController
        extends BaseController<Integer, VehicleTypeInfo, VehicleTypeInfo>
        implements ISystemVehicleTypeController {

    private final ISystemVehicleTypeFactory vehicleFactory;

    @Override
    protected IDataFactory<Integer, VehicleTypeInfo, VehicleTypeInfo> getDataFactory() {
        return vehicleFactory;
    }
}
