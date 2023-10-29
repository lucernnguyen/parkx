package org.parkz.modules.media.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum MediaErrorCode implements IErrorCode {

    MEDIA_FILE_NOT_FOUND(1, StatusMapping.BAD_REQUEST),
    ;

    private final int code;
    private final StatusMapping statusMapping;
}
