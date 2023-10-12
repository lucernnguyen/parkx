package org.parkz.modules.parking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class ParkingInfo implements IBaseData<UUID> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Schema(example = "Bãi giữ xe FPT")
    private String name;
    @Schema(example = "Đây là mô tả")
    private String description;
    @Builder.Default
    @Schema(example = "true")
    private boolean active = true;
    @Builder.Default
    @Schema(example = "false")
    private boolean fullSlot = true;
}
