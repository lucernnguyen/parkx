package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.UUID;

@Getter
@Setter
@ToString
@SuperBuilder(toBuilder = true)
@Jacksonized
public class UserInfo implements IBaseData<String> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private String id;
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
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private String username;
    private UUID groupId;
}
