package org.parkz.modules.media.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.parkz.modules.media.enums.AccessType;
import org.parkz.modules.media.enums.FileType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequest {

    @NotNull
    @Schema(example = "AVATAR")
    private FileType fileType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private MultipartFile file;

    @JsonIgnore
    private AccessType accessType;
}
