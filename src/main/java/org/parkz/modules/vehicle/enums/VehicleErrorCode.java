package org.parkz.modules.vehicle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum VehicleErrorCode implements IErrorCode {

    VEHICLE_LICENSE_PLATE_ALREADY_EXISTS(1, StatusMapping.ALREADY_EXISTS);

    ;
    private final int code;
    private final StatusMapping statusMapping;
}
