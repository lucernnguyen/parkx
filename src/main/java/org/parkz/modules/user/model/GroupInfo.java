package org.parkz.modules.user.model;

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
    private String name;
    private String description;
    private GroupKind kind;
    @Builder.Default
    private boolean defaultGroup = false;

}
