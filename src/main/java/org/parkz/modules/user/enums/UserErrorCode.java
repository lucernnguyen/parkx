package org.parkz.modules.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements IErrorCode {

    USER_NOT_FOUND(1, StatusMapping.NOT_FOUND),
    FIREBASE_NOT_FOUND(2, StatusMapping.NOT_FOUND),
    USER_PHONE_ALREADY_EXISTS(3, StatusMapping.ALREADY_EXISTS),
    USER_EMAIL_ALREADY_EXISTS(4, StatusMapping.ALREADY_EXISTS),

    ;

    private final int code;
    private final StatusMapping statusMapping;
}
