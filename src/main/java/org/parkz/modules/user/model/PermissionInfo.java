package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@JsonView(View.ExtendedDetails.class)
public class PermissionInfo implements IBaseData<String> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    private String name;
    private String action;
    @Builder.Default
    private boolean showMenu = true;
    private String description;
    private String nameGroup;
}
