package org.parkz.modules.parking_session.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.filter.AppParkingSessionFilter;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.parkz.modules.parking_session.model.request.InitCheckoutRequest;
import org.springframework.fastboot.rest.common.controller.IGetDetailByIdController;
import org.springframework.fastboot.rest.common.controller.IGetInfoListWithFilterController;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "App Parking Session Controller")
@RequestMapping("/api/v1/app/parkingSession")
public interface IAppParkingSessionController extends
        IGetDetailByIdController<UUID, ParkingSessionInfo>,
        IGetInfoListWithFilterController<UUID, ParkingSessionInfo, AppParkingSessionFilter> {

    @PostMapping("/checkIn")
    @Operation(
            description = "[CHECKIN][STEP 1][User] Create check in session, gen QR code = sessionId"
    )
    ResponseEntity<BaseResponse<ParkingSessionInfo>> checkIn(@Valid @RequestBody CreateParkingSessionRequest request);

    @Override
    @GetMapping("{id}/detail")
    @Operation(
            description = "[CHECKIN][STEP 2][Staff] Get session info, staff check info in result"
    )
    default ResponseEntity<BaseResponse<ParkingSessionInfo>> getDetailById(@PathVariable("id") UUID id) {
        return IGetDetailByIdController.super.getDetailById(id);
    }

    @PutMapping("/confirm")
    @Operation(
            description = "[CHECKIN][STEP 3][Staff] Confirm check in"
    )
    ResponseEntity<BaseResponse<SuccessResponse>> confirm(@Valid @RequestBody ConfirmCheckInRequest request);

    @PostMapping("/initCheckOut")
    @Operation(
            description = "[CHECKOUT][STEP 1][User] Init checkout action with sessionId and paymentType"
    )
    ResponseEntity<BaseResponse<SuccessResponse>> initCheckout(@Valid @RequestBody InitCheckoutRequest request);

    @PutMapping("/checkOut")
    @Operation(
            description = "[CHECKOUT][STEP 2][Staff] Confirm check out"
    )
    ResponseEntity<BaseResponse<SuccessResponse>> checkout(@Valid @RequestBody ConfirmCheckOutRequest request);
}
