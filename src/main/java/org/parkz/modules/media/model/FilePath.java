package org.parkz.modules.media.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parkz.modules.media.enums.AccessType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilePath {

    @NotBlank
    protected String fileType;

    @NotBlank
    protected String objectName;

    @JsonIgnore
    protected AccessType accessType;

    protected FilePath(String fileType, String objectName) {
        this.fileType = fileType;
        this.objectName = objectName;
    }
}
