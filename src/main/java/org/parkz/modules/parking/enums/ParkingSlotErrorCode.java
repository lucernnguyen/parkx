package org.parkz.modules.parking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum ParkingSlotErrorCode implements IErrorCode {

    PARKING_SLOT_NOT_FOUND(1, StatusMapping.NOT_FOUND),
    PARKING_SLOT_POSITION_ALREADY_EXISTS(2, StatusMapping.ALREADY_EXISTS),
    PARKING_SLOT_POSITION_ALREADY_USED(3, StatusMapping.CONFLICT)
    ;

    private final int code;
    private final StatusMapping statusMapping;
}
