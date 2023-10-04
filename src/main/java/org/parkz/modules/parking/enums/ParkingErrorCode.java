package org.parkz.modules.parking.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum ParkingErrorCode implements IErrorCode {

    PARKING_NOT_INITIALIZE(1, StatusMapping.BAD_REQUEST)

    ;

    private final int code;
    private final StatusMapping statusMapping;
}
