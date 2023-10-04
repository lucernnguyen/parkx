package org.parkz.modules.parking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.parkz.modules.parking.model.ParkingInfo;
import org.springframework.fastboot.rest.common.controller.IGetDetailByContextController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Tag(name = "App Parking Controller")
@RequestMapping("/api/v1/app/parking")
public interface IAppParkingController extends
        IGetDetailByContextController<UUID, ParkingInfo> {
}
