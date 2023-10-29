package org.parkz.modules.media.factory;

import org.parkz.modules.media.model.FileDownloaded;
import org.parkz.modules.media.model.FilePath;
import org.parkz.modules.media.model.PreSignUrl;
import org.parkz.modules.media.model.UploadResult;
import org.parkz.modules.media.model.request.BulkUploadPreSignPrivateRequest;
import org.parkz.modules.media.model.request.UploadRequest;
import org.parkz.modules.media.model.request.UploadWithPreSignRequest;
import org.parkz.modules.media.model.response.UploadWithPreSignResponse;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;

import java.util.List;

public interface IMediaFactory {
    List<UploadWithPreSignResponse> bulkUploadPreSign(BulkUploadPreSignPrivateRequest request);

    SuccessResponse deleteFile(FilePath request) throws InvalidException;

    PreSignUrl previewWithPreSigned(FilePath request) throws InvalidException;

    FileDownloaded preview(FilePath request) throws InvalidException;

    UploadWithPreSignResponse uploadWithPreSign(UploadWithPreSignRequest request);

    UploadResult upload(UploadRequest request) throws InvalidException;
}
