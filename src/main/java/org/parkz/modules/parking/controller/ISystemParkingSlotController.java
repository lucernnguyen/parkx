package org.parkz.modules.parking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking.model.filter.SystemSlotParkingFilter;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.fastboot.rest.common.controller.transactional.ICreateModelTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IDeleteModelByIdTransactionalController;
import org.springframework.fastboot.rest.common.controller.transactional.IUpdateModelTransactionalController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "System Parking Slot Controller")
@RequestMapping("/api/v1/system/parkingSlots")
public interface ISystemParkingSlotController extends
        ICreateModelTransactionalController<UUID, ParkingSlotInfo>,
        IGetInfoPageController<UUID, ParkingSlotInfo, SystemSlotParkingFilter>,
        IGetDetailByIdController<UUID, ParkingSlotInfo>,
        IUpdateModelTransactionalController<UUID, ParkingSlotInfo>,
        IDeleteModelByIdTransactionalController<UUID> {
}
