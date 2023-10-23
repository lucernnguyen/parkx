package org.parkz.modules.vehicle.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.math.BigDecimal;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonView(View.ExtendedDetails.class)
public class VehicleTypeInfo implements IBaseData<Integer> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private Integer id;
    @NotBlank
    @Schema(example = "Xe Test")
    private String name;
    @Builder.Default
    @Schema(example = "false", defaultValue = "false")
    private boolean active = false;
    @Builder.Default
    @Schema(example = "100", defaultValue = "0")
    private Integer totalSlot = 0;
    @DecimalMin("1000")
    private BigDecimal price;
}
