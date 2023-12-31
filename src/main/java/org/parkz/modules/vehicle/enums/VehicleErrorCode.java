package org.parkz.modules.vehicle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum VehicleErrorCode implements IErrorCode {

    VEHICLE_NOT_FOUND(1, StatusMapping.NOT_FOUND),
    VEHICLE_LICENSE_PLATE_ALREADY_EXISTS(2, StatusMapping.ALREADY_EXISTS);

    ;
    private final int code;
    private final StatusMapping statusMapping;
}
