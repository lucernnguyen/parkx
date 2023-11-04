package org.parkz.modules.parking_session.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum ParkingSessionErrorCode implements IErrorCode {

    PARKING_SESSION_NOT_FOUND(1, StatusMapping.NOT_FOUND),
    PARKING_SESSION_DUPLICATE_VEHICLE_CHECKIN(2, StatusMapping.CONFLICT),
    PARKING_SESSION_CHECKED_OUT(3, StatusMapping.BAD_REQUEST),

    ;
    private final int code;
    private final StatusMapping statusMapping;
}
