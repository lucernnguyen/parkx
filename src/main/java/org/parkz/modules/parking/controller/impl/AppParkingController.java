package org.parkz.modules.parking.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.controller.IAppParkingController;
import org.parkz.modules.parking.factory.app.IAppParkingFactory;
import org.parkz.modules.parking.model.ParkingInfo;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppParkingController
        extends BaseController<UUID, ParkingInfo, ParkingInfo>
        implements IAppParkingController {

    private final IAppParkingFactory appParkingFactory;

    @Override
    protected IDataFactory<UUID, ParkingInfo, ParkingInfo> getDataFactory() {
        return appParkingFactory;
    }
}
