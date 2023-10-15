package org.parkz.modules.user.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.Collections;
import java.util.List;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonView(View.ExtendedDetails.class)
public class GroupDetails extends GroupInfo {

    @Builder.Default
    private List<String> permissions = Collections.emptyList();
}
