package org.parkz.modules.media.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.parkz.modules.media.model.PreSignUrl;
import org.parkz.modules.media.model.UploadResult;
import org.parkz.modules.media.model.request.BulkUploadPreSignPrivateRequest;
import org.parkz.modules.media.model.request.UploadRequest;
import org.parkz.modules.media.model.request.UploadWithPreSignRequest;
import org.parkz.modules.media.model.response.UploadWithPreSignResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

@Tag(name = "Media Controller", description = "Upload & download file")
@Validated
@RequestMapping(value = "/api/v1/media")
public interface IMediaController {

    @Operation(summary = "Upload file")
    @PostMapping(value = "/private/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<UploadResult>> uploadPrivate(@Valid @ModelAttribute UploadRequest request);


    @Operation(summary = "Request PreSigned URL Upload file")
    @GetMapping(value = "/private/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<UploadWithPreSignResponse>> uploadPreSignPrivate(@Valid @ParameterObject UploadWithPreSignRequest request);

    @Operation(summary = "Request PreSigned URL Upload file in bulk")
    @PostMapping(value = "/private/upload/bulk")
    ResponseEntity<BaseResponse<List<UploadWithPreSignResponse>>> bulkUploadPreSignPrivate(@Valid @RequestBody BulkUploadPreSignPrivateRequest request);

    @Operation(summary = "Download file")
    @GetMapping(value = "/private/preview/{fileType}/{objectName}")
    ResponseEntity<StreamingResponseBody> previewPrivate(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                                                         @NotBlank @PathVariable String objectName,
                                                         HttpServletResponse context);

    @Operation(
            summary = "Preview file private",
            description = "Preview file private, redirect to AWS S3 with pre-signed URL"
    )
    @GetMapping(value = "/private/preview/pre-sign/{fileType}/{objectName}")
    void previewPreSignPrivate(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                               @NotBlank @PathVariable String objectName,
                               HttpServletResponse context) throws IOException;

    @Operation(
            summary = "Get pre-sign url as string"
    )
    @GetMapping(value = "/private/preview/pre-sign-url/{fileType}/{objectName}")
    ResponseEntity<BaseResponse<PreSignUrl>> getPreSignDownloadPrivate(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                                                                       @NotBlank @PathVariable String objectName);

    // @Operation(summary = "Delete file")
    // @DeleteMapping(value = "/private/delete/{fileType}/{objectName}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<SuccessResponse>> deletePrivate(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                                                                @NotBlank @PathVariable String objectName);

    @Operation(
            summary = "Upload file public",
            description = "Upload file public with file type"
    )
    @PostMapping(value = "/public/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse<UploadResult>> uploadPublic(@Valid @ModelAttribute UploadRequest request);

    @Operation(
            summary = "Preview file public",
            description = "Preview file public, redirect to AWS S3 with pre-signed URL"
    )
    @GetMapping(value = "/public/preview/{fileType}/{objectName}")
    ResponseEntity<StreamingResponseBody> previewPubic(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                                                       @NotBlank @PathVariable String objectName,
                                                       HttpServletResponse context);

    @Operation(
            summary = "Preview file public",
            description = "Preview file public, redirect to AWS S3 with pre-signed URL"
    )
    @GetMapping(value = "/public/preview/pre-sign/{fileType}/{objectName}")
    void previewPreSignPublic(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                              @NotBlank @PathVariable String objectName,
                              HttpServletResponse context) throws IOException;

    @Operation(
            summary = "Get pre-sign url as string"
    )
    @GetMapping(value = "/public/preview/pre-sign-url/{fileType}/{objectName}")
    ResponseEntity<BaseResponse<PreSignUrl>> getPreSignDownloadPublic(@NotBlank @PathVariable @Schema(example = "avatars") String fileType,
                                                                      @NotBlank @PathVariable String objectName);
}
