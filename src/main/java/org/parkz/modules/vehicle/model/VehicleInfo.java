package org.parkz.modules.vehicle.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
@JsonView(View.Details.class)
public class VehicleInfo implements IBaseData<UUID> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @NotBlank
    @Schema(example = "Wave Tàu")
    private String name;
    @NotBlank
    @Schema(example = "51-B1 99999")
    private String licensePlate;
    @NotBlank
    @Schema(example = "Đen")
    private String color;
    @Builder.Default
    private boolean checkin = false;
    @Builder.Default
    @Schema(example = "[\"https://congthuong-cdn.mastercms.vn/stores/news_dataimages/2023/072023/27/17/xe-may-sh20230727173628.jpg\"]")
    private List<String> images = Collections.emptyList();
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private Integer vehicleTypeId;
    private String vehicleTypeName;

}
