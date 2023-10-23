package org.parkz.modules.vehicle.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IDeleteModelByIdTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "System Vehicle Type Controller")
@RequestMapping("/api/v1/system/vehicleTypes")
public interface ISystemVehicleTypeController extends
        ICreateModelTransactionalController<Integer, VehicleTypeInfo>,
        IGetInfoListController<Integer, VehicleTypeInfo>,
        IGetDetailByIdController<Integer, VehicleTypeInfo>,
        IUpdateModelTransactionalController<Integer, VehicleTypeInfo>,
        IDeleteModelByIdTransactionalController<Integer> {
}
