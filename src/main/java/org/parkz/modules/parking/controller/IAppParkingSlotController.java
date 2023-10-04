package org.parkz.modules.parking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking.model.filter.SystemSlotParkingFilter;
import org.springframework.fastboot.rest.common.controller.IGetInfoPageController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "App Parking Slot Controller")
@RequestMapping("/api/v1/app/parkingSlots")
public interface IAppParkingSlotController extends
        IGetInfoPageController<UUID, ParkingSlotInfo, SystemSlotParkingFilter> {
}
