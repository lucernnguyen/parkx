package org.parkz.modules.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.Collections;
import java.util.List;

@Data
@Jacksonized
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class GroupDetails extends GroupInfo {

    @Builder.Default
    private List<String> permissions = Collections.emptyList();
}
