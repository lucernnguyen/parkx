package org.parkz.modules.vehicle.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoListController;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IDeleteModelByIdTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "App Vehicle Controller")
@RequestMapping("/api/v1/app/vehicle")
public interface IAppVehicleController extends
        ICreateModelTransactionalController<UUID, VehicleInfo>,
        IGetInfoListController<UUID, VehicleInfo>,
        IGetDetailByIdController<UUID, VehicleInfo>,
        IUpdateModelTransactionalController<UUID, VehicleInfo>,
        IDeleteModelByIdTransactionalController<UUID> {
}
