package org.parkz.modules.parking_session.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.parkz.modules.parking_session.model.request.ConfirmCheckInRequest;
import org.parkz.modules.parking_session.model.request.ConfirmCheckOutRequest;
import org.parkz.modules.parking_session.model.request.CreateParkingSessionRequest;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "App Parking Session Controller")
@RequestMapping("/api/v1/app/parkingSession")
public interface IAppParkingSessionController {

    @PostMapping("/checkIn")
    @Operation(
            description = "[STEP 1] Create check in session, gen QR code = sessionId"
    )
    ResponseEntity<BaseResponse<ParkingSessionInfo>> checkIn(@Valid @RequestBody CreateParkingSessionRequest request);

    @GetMapping("/{id}")
    @Operation(
            description = "[STEP 2] Get session info, staff check info in result"
    )
    ResponseEntity<BaseResponse<ParkingSessionInfo>> getSessionInfo(@PathVariable("id") UUID id);

    @PutMapping("/confirm")
    @Operation(
            description = "[STEP 3] Staff confirm check in"
    )
    ResponseEntity<BaseResponse<SuccessResponse>> confirm(@Valid @RequestBody ConfirmCheckInRequest request);

    @PutMapping("/checkOut")
    @Operation(
            description = "[STEP 4] Confirm checkout"
    )
    ResponseEntity<BaseResponse<SuccessResponse>> checkout(@Valid @RequestBody ConfirmCheckOutRequest request);
}
