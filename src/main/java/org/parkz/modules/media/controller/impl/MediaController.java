package org.parkz.modules.media.controller.impl;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.parkz.modules.media.controller.IMediaController;
import org.parkz.modules.media.enums.AccessType;
import org.parkz.modules.media.factory.IMediaFactory;
import org.parkz.modules.media.model.FileDownloaded;
import org.parkz.modules.media.model.FilePath;
import org.parkz.modules.media.model.PreSignUrl;
import org.parkz.modules.media.model.UploadResult;
import org.parkz.modules.media.model.request.BulkUploadPreSignPrivateRequest;
import org.parkz.modules.media.model.request.UploadRequest;
import org.parkz.modules.media.model.request.UploadWithPreSignRequest;
import org.parkz.modules.media.model.response.UploadWithPreSignResponse;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.exception.RestException;
import org.springframework.fastboot.rest.common.factory.response.IResponseFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.fastboot.util.function.Supplier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MediaController implements IMediaController {

    private final IMediaFactory mediaFactory;
    private final IResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseResponse<UploadResult>> uploadPrivate(UploadRequest request) {
        request.setAccessType(AccessType.PRIVATE);
        return wrapResponse(() -> mediaFactory.upload(request));
    }

    @Override
    public ResponseEntity<BaseResponse<UploadWithPreSignResponse>> uploadPreSignPrivate(UploadWithPreSignRequest request) {
        request.setAccessType(AccessType.PRIVATE);
        return wrapResponse(() -> mediaFactory.uploadWithPreSign(request));
    }

    @Override
    public ResponseEntity<BaseResponse<List<UploadWithPreSignResponse>>> bulkUploadPreSignPrivate(BulkUploadPreSignPrivateRequest request) {
        return wrapResponse(() -> mediaFactory.bulkUploadPreSign(request));
    }

    @Override
    public ResponseEntity<StreamingResponseBody> previewPrivate(String fileType, String objectName, HttpServletResponse context) {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PRIVATE);
        try {
            return preview(filePath);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    @Override
    public void previewPreSignPrivate(String fileType, String objectName, HttpServletResponse context) throws IOException {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PRIVATE);
        try {
            previewPreSign(filePath, context);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<PreSignUrl>> getPreSignDownloadPrivate(String fileType, String objectName) {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PRIVATE);
        return wrapResponse(() -> mediaFactory.previewWithPreSigned(filePath));
    }

    @Override
    public ResponseEntity<BaseResponse<SuccessResponse>> deletePrivate(String fileType, String objectName) {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PRIVATE);
        return wrapResponse(() -> mediaFactory.deleteFile(filePath));
    }

    @Override
    public ResponseEntity<BaseResponse<UploadResult>> uploadPublic(UploadRequest request) {
        request.setAccessType(AccessType.PUBLIC);
        return wrapResponse(() -> mediaFactory.upload(request));
    }

    @Override
    public ResponseEntity<StreamingResponseBody> previewPubic(String fileType, String objectName, HttpServletResponse context) {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PUBLIC);
        try {
            return preview(filePath);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    @Override
    public void previewPreSignPublic(String fileType, String objectName, HttpServletResponse context) throws IOException {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PUBLIC);
        try {
            previewPreSign(filePath, context);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    @Override
    public ResponseEntity<BaseResponse<PreSignUrl>> getPreSignDownloadPublic(String fileType, String objectName) {
        FilePath filePath = new FilePath(fileType, objectName, AccessType.PUBLIC);
        return wrapResponse(() -> mediaFactory.previewWithPreSigned(filePath));
    }

    private <SR> ResponseEntity<BaseResponse<SR>> wrapResponse(Supplier<SR> supplier) {
        try {
            SR result = supplier.get();
            return responseFactory.success(result);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }

    private ResponseEntity<StreamingResponseBody> preview(FilePath request) throws InvalidException {
        FileDownloaded response = mediaFactory.preview(request);
        var resultBuilder = ResponseEntity.ok()
                .contentType(response.getContentType());
        if (!response.getContentType().getType().startsWith("image")) {
            resultBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + response.getFileName());
        }
        StreamingResponseBody streamingResponseBody = out -> IOUtils.copy(response.getInputStream(), out);
        return resultBuilder.body(streamingResponseBody);
    }

    private void previewPreSign(FilePath request, HttpServletResponse context) throws InvalidException, IOException {
        PreSignUrl response = mediaFactory.previewWithPreSigned(request);
        if (response.getUrl() != null) {
            context.sendRedirect(response.getUrl());
        }
    }
}
