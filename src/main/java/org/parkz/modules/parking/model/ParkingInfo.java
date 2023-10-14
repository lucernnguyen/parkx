package org.parkz.modules.parking.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class ParkingInfo implements IBaseData<UUID> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private UUID id;
    @NotBlank
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
