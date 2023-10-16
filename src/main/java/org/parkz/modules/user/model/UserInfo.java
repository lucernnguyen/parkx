package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
@NoArgsConstructor
@JsonView(View.Details.class)
public class UserInfo implements IBaseData<String> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private String id;
    @NotBlank
    @Schema(example = "Nguyễn Văn A")
    private String name;
    @NotBlank
    @Schema(example = "nguyenvana@gmail.com")
    private String email;
    @NotBlank
    @Schema(example = "0123456789")
    private String phone;
    @Schema(example = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
    private String avatar;
    @Schema(example = "true")
    @Builder.Default
    private boolean active = true;
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private String username;
    @JsonIgnore
    @Schema(hidden = true)
    private String password;
    private UUID groupId;
}
