package org.parkz.modules.vehicle.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.controller.IAppVehicleTypeController;
import org.parkz.modules.vehicle.factory.app.IAppVehicleTypeFactory;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppVehicleTypeController
        extends BaseController<Integer, VehicleTypeInfo, VehicleTypeInfo>
        implements IAppVehicleTypeController {

    private final IAppVehicleTypeFactory appVehicleTypeFactory;

    @Override
    protected IDataFactory<Integer, VehicleTypeInfo, VehicleTypeInfo> getDataFactory() {
        return appVehicleTypeFactory;
    }
}
