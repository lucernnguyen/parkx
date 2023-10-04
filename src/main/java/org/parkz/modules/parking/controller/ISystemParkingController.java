package org.parkz.modules.parking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.parking.model.ParkingInfo;
import org.springframework.fastboot.rest.common.controller.IGetDetailByContextController;
import org.springframework.fastboot.rest.common.controller.IUpsertModelController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "System Parking Controller")
@RequestMapping("/api/v1/system/parking")
public interface ISystemParkingController extends
        IUpsertModelController<UUID, ParkingInfo>,
        IGetDetailByContextController<UUID, ParkingInfo> {
}
