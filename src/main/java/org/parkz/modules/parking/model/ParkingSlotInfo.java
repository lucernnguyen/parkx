package org.parkz.modules.parking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
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
public class ParkingSlotInfo implements IBaseData<UUID> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    private String name;
    private Integer rowIndex;
    private Integer columnIndex;
    @Builder.Default
    private boolean hasParking = false;
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private UUID parkingId;
}
