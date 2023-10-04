package org.parkz.modules.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.BaseData;

import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Jacksonized
public class UserInfo extends BaseData<String> {

    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(example = "Nguyễn Văn A")
    private String name;
    @NotBlank
    @Size(min = 1, max = 50)
    @Schema(example = "nguyenvana@gmail.com")
    private String email;
    @NotBlank
    @Size(min = 1, max = 10)
    @Schema(example = "0123456789")
    private String phone;
    @Schema(example = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
    private String avatar;
    @Schema(example = "true")
    @Builder.Default
    private boolean active = true;
    private String username;
    private UUID groupId;
}