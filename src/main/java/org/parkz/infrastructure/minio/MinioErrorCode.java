package org.parkz.infrastructure.minio;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.exception.StatusMapping;

@Getter
@RequiredArgsConstructor
public enum MinioErrorCode implements IErrorCode {


    MINIO_STORAGE_ERROR(1, StatusMapping.INTERNAL_SERVER_ERROR),
    MINIO_FILE_FETCHING_ERROR(2, StatusMapping.INTERNAL_SERVER_ERROR),
    MINIO_FILE_METADATA_FETCHING_ERROR(3, StatusMapping.INTERNAL_SERVER_ERROR),
    MINIO_FILE_UPLOAD_FAILED(4, StatusMapping.INTERNAL_SERVER_ERROR),
    FILE_DELETING_ERROR(5, StatusMapping.INTERNAL_SERVER_ERROR);
    private final int code;
    private final StatusMapping statusMapping;
}
