package org.parkz.modules.parking.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
@NoArgsConstructor
public class ParkingSlotInfo implements IBaseData<UUID> {

    @JsonView({View.Exclude.Create.class, View.Include.Update.class})
    private UUID id;
    private String name;
    private Integer rowIndex;
    private Integer columnIndex;
    @Builder.Default
    private boolean hasParking = false;
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private UUID parkingId;
}
