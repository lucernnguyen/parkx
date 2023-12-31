package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.user.enums.GroupKind;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.UUID;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@JsonView(View.ExtendedDetails.class)
public class GroupInfo implements IBaseData<UUID> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private UUID id;
    @NotBlank
    @Size(min = 1, max = 50)
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private String name;
    private String description;
    @NotNull
    private GroupKind kind;
    @Builder.Default
    private boolean defaultGroup = false;

}
