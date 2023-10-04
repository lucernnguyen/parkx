package org.parkz.modules.parking.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.controller.ISystemParkingSlotController;
import org.parkz.modules.parking.factory.system.ISystemParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SystemParkingSlotController
        extends BaseController<UUID, ParkingSlotInfo, ParkingSlotInfo>
        implements ISystemParkingSlotController {

    private final ISystemParkingSlotFactory systemParkingSlotFactory;

    @Override
    protected IDataFactory<UUID, ParkingSlotInfo, ParkingSlotInfo> getDataFactory() {
        return systemParkingSlotFactory;
    }
}
