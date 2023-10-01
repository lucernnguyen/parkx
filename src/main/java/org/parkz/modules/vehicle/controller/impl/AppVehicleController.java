package org.parkz.modules.vehicle.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.controller.IAppVehicleController;
import org.parkz.modules.vehicle.factory.IVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppVehicleController
        extends BaseController<UUID, VehicleInfo, VehicleInfo>
        implements IAppVehicleController {

    @Qualifier("appVehicleFactory")
    private final IVehicleFactory vehicleFactory;

    @Override
    protected IDataFactory<UUID, VehicleInfo, VehicleInfo> getDataFactory() {
        return vehicleFactory;
    }
}
