package org.parkz.modules.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.BaseData;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class PermissionInfo extends BaseData<String> {

    private String name;
    private String action;
    @Builder.Default
    private boolean showMenu = true;
    private String description;
    private String nameGroup;
}
