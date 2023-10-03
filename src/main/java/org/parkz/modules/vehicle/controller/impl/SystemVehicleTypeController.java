package org.parkz.modules.vehicle.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.controller.ISystemVehicleTypeController;
import org.parkz.modules.vehicle.factory.IVehicleTypeFactory;
import org.parkz.modules.vehicle.model.VehicleTypeDetails;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SystemVehicleTypeController
        extends BaseController<Integer, VehicleTypeInfo, VehicleTypeDetails>
        implements ISystemVehicleTypeController {

    @Qualifier("vehicleTypeFactory")
    private final IVehicleTypeFactory vehicleFactory;

    @Override
    protected IDataFactory<Integer, VehicleTypeInfo, VehicleTypeDetails> getDataFactory() {
        return vehicleFactory;
    }
}
