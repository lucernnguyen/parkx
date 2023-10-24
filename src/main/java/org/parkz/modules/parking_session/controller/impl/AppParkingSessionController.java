package org.parkz.modules.parking_session.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking_session.controller.IAppParkingSessionController;
import org.parkz.modules.parking_session.factory.app.IAppParkingSessionFactory;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.parking_session.model.request.InitCheckoutRequest;
import org.springframework.fastboot.rest.common.controller.base.BaseController;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AppParkingSessionController
        extends BaseController<UUID, ParkingSessionInfo, ParkingSessionInfo>
        implements IAppParkingSessionController {

    private final IAppParkingSessionFactory appParkingSessionFactory;

    @Override
    public ResponseEntity<BaseResponse<ParkingSessionInfo>> checkIn(CreateParkingSessionRequest request) {
        return wrapResponse(() -> appParkingSessionFactory.checkin(request));
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> confirm(ConfirmCheckInRequest request) {
        return wrapResponse(() -> appParkingSessionFactory.confirmCheckIn(request));
    }

    public ResponseEntity<BaseResponse<SuccessResponse>> initCheckout(InitCheckoutRequest request) {
        return wrapResponse(() -> appParkingSessionFactory.initCheckout(request));
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> checkout(ConfirmCheckOutRequest request) {
        return wrapResponse(() -> appParkingSessionFactory.checkOut(request));
    }

    @Override
    protected IDataFactory<UUID, ParkingSessionInfo, ParkingSessionInfo> getDataFactory() {
        return appParkingSessionFactory;
    }
}
