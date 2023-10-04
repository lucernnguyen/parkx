package org.parkz.modules.parking.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.controller.IAppParkingSlotController;
import org.parkz.modules.parking.factory.app.IAppParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppParkingSlotController
        extends BaseController<UUID, ParkingSlotInfo, ParkingSlotInfo>
        implements IAppParkingSlotController {

    private final IAppParkingSlotFactory appParkingSlotFactory;

    @Override
    protected IDataFactory<UUID, ParkingSlotInfo, ParkingSlotInfo> getDataFactory() {
        return appParkingSlotFactory;
    }
}
