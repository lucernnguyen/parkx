package org.parkz.infrastructure.minio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.CustomException;
import org.springframework.fastboot.exception.IErrorCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class MinioException extends RuntimeException implements CustomException {

    private final transient IErrorCode errorCode;
    private final Map<String, String> args;

    public MinioException(IErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public MinioException(IErrorCode errorCode, Throwable cause) {
        this(errorCode, null, cause);
    }

    public MinioException(IErrorCode errorCode, Map<String, String> args, Throwable cause) {
        super(CustomException.errorResponse(errorCode, args), cause);
        this.errorCode = errorCode;
        this.args = Objects.requireNonNullElseGet(args, HashMap::new);
    }

    @SuppressWarnings({"unchecked"})
    public MinioException append(String key, String value) {
        this.args.put(key, value);
        return this;
    }
}
