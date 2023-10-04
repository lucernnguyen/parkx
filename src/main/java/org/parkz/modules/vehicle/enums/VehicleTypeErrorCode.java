package org.parkz.modules.vehicle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum VehicleTypeErrorCode implements IErrorCode {

    VEHICLE_TYPE_NOT_FOUND(1, StatusMapping.NOT_FOUND),

    ;
    private final int code;
    private final StatusMapping statusMapping;
}
