package org.parkz.modules.vehicle.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class VehicleTypeInfo implements IBaseData<Integer> {

    @NotNull
    private Integer id;
    @NotBlank
    @Schema(example = "Xe Test")
    private String name;
}
