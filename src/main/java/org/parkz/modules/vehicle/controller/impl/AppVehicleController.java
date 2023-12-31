package org.parkz.modules.vehicle.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.controller.IAppVehicleController;
import org.parkz.modules.vehicle.factory.app.IAppVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppVehicleController
        extends BaseController<UUID, VehicleInfo, VehicleInfo>
        implements IAppVehicleController {

    private final IAppVehicleFactory vehicleFactory;

    @Override
    protected IDataFactory<UUID, VehicleInfo, VehicleInfo> getDataFactory() {
        return vehicleFactory;
    }
}
