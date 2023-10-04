package org.parkz.modules.parking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum ParkingSlotErrorCode implements IErrorCode {

    PARKING_SLOT_POSITION_ALREADY_EXISTS(1, StatusMapping.ALREADY_EXISTS)
    ;

    private final int code;
    private final StatusMapping statusMapping;
}
