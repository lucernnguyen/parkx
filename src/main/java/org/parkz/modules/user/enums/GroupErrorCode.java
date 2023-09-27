package org.parkz.modules.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum GroupErrorCode implements IErrorCode {

    USERS_EXISTED_IN_GROUP(1, StatusMapping.BAD_REQUEST),
    GROUP_NAME_EXISTED(2, StatusMapping.BAD_REQUEST),
    GROUP_NOT_FOUND(3, StatusMapping.NOT_FOUND),

    ;

    private final int code;
    private final StatusMapping statusMapping;
}
