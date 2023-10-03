package org.parkz.modules.auth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum AuthenticationErrorCode implements IErrorCode {

    SUBJECT_ID_EXISTED(1, StatusMapping.CONFLICT)
    ;

    private final int code;
    private final StatusMapping statusMapping;
}
