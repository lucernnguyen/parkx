package org.parkz.modules.parking.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.controller.ISystemParkingController;
import org.parkz.modules.parking.factory.system.ISystemParkingFactory;
import org.parkz.modules.parking.model.ParkingInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SystemParkingController
        extends BaseController<UUID, ParkingInfo, ParkingInfo>
        implements ISystemParkingController {

    private final ISystemParkingFactory systemParkingFactory;

    @Override
    protected IDataFactory<UUID, ParkingInfo, ParkingInfo> getDataFactory() {
        return systemParkingFactory;
    }
}
