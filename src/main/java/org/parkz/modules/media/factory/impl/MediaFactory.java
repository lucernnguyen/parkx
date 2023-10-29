package org.parkz.modules.media.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.infrastructure.minio.MinioService;
import org.parkz.modules.media.enums.AccessType;
import org.parkz.modules.media.factory.IMediaFactory;
import org.parkz.modules.media.model.*;
import org.parkz.modules.media.model.request.BulkUploadPreSignPrivateRequest;
import org.parkz.modules.media.model.request.UploadRequest;
import org.parkz.modules.media.model.request.UploadWithPreSignRequest;
import org.parkz.modules.media.model.response.UploadWithPreSignResponse;
import org.parkz.shared.utils.FileNameUtils;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaFactory implements IMediaFactory {

    private final MinioService minioService;

    @Override
    public List<UploadWithPreSignResponse> bulkUploadPreSign(BulkUploadPreSignPrivateRequest request) {
        List<UploadWithPreSignResponse> listUrl = new ArrayList<>();
        for (var file : request.getFileMetadatas()) {
            FileInfo fileInfo = new FileInfo(file.getFileType(), FileNameUtils.generateFileName(), AccessType.PRIVATE, file.getExtension());
            String preSignedUrl = minioService.generatePreSignedUrlUpload(fileInfo.getUploadPath());
            UploadWithPreSignResponse response = new UploadWithPreSignResponse(preSignedUrl, fileInfo.getPreviewUrl());
            listUrl.add(response);
        }
        return listUrl;
    }

    @Override
    public SuccessResponse deleteFile(FilePath request) throws InvalidException {
        FileInfo fileInfo = new FileInfo(request.getFileType(), request.getObjectName(), AccessType.PRIVATE);
        minioService.remove(fileInfo.getUploadPath());
        return SuccessResponse.status(true);
    }

    @Override
    public PreSignUrl previewWithPreSigned(FilePath request) throws InvalidException {
        FileInfo fileInfo = new FileInfo(request.getFileType(), request.getObjectName(), request.getAccessType());
        return new PreSignUrl(minioService.generatePreSignedUrlDownload(fileInfo.getUploadPath()));
    }

    @Override
    public FileDownloaded preview(FilePath request) throws InvalidException {
        FileInfo fileInfo = new FileInfo(request.getFileType(), request.getObjectName(), request.getAccessType());
        return minioService.get(fileInfo.getUploadPath());
    }

    @Override
    public UploadWithPreSignResponse uploadWithPreSign(UploadWithPreSignRequest request) {
        FileInfo fileInfo = new FileInfo(request.getFileType(), FileNameUtils.generateFileName(), AccessType.PRIVATE, request.getExtension());
        return new UploadWithPreSignResponse(
                minioService.generatePreSignedUrlUpload(fileInfo.getUploadPath()),
                fileInfo.getPreviewUrl()
        );
    }

    @Override
    public UploadResult upload(UploadRequest request) throws InvalidException {
        FileInfo fileInfo = new FileInfo(request.getFileType(), FileNameUtils.generateFileName(), request.getAccessType(), request.getFile());
        minioService.upload(fileInfo);
        return new UploadResult(fileInfo);
    }

}
