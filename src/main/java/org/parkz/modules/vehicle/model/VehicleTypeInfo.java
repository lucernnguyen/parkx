package org.parkz.modules.vehicle.model;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

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
}
