package org.parkz.modules.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum GroupErrorCode implements IErrorCode {

    GROUP_HAVE_USERS_EXISTED_IN(1, StatusMapping.BAD_REQUEST),
    GROUP_NAME_EXISTED(2, StatusMapping.CONFLICT),
    GROUP_NOT_FOUND(3, StatusMapping.NOT_FOUND),
    GROUP_IS_DEFAULT(4, StatusMapping.BAD_REQUEST)
    ;

    private final int code;
    private final StatusMapping statusMapping;
}
