package org.parkz.modules.media.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.parkz.modules.media.enums.AccessType;
import org.parkz.modules.media.enums.FileType;

@Data
public class UploadWithPreSignRequest {

    @NotNull
    @Schema(example = "AVATAR")
    private FileType fileType;

    @Schema(example = "png")
    private String extension;

    @JsonIgnore
    private AccessType accessType;

}
