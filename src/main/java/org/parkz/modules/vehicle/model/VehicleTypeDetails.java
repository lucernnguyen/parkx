package org.parkz.modules.vehicle.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class VehicleTypeDetails extends VehicleTypeInfo {

    @Builder.Default
    @Schema(example = "false", defaultValue = "false")
    private boolean active = false;
    @Builder.Default
    @Schema(example = "100", defaultValue = "0")
    private Integer totalSlot = 0;
}
