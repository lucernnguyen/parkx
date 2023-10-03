package org.parkz.modules.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.user.enums.GroupKind;
import org.springframework.fastboot.rest.common.model.BaseData;

import java.util.UUID;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class GroupInfo extends BaseData<UUID> {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
    private String description;
    @NotNull
    private GroupKind kind;
    @Builder.Default
    private boolean defaultGroup = false;

}
