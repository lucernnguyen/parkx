package org.parkz.modules.vehicle.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "App Vehicle Type Controller")
@RequestMapping("/api/v1/app/vehicleTypes")
public interface IAppVehicleTypeController extends
        IGetInfoListController<Integer, VehicleTypeInfo> {
}
