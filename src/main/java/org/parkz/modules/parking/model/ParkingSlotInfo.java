package org.parkz.modules.parking.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.fastboot.rest.common.model.IBaseData;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class ParkingSlotInfo implements IBaseData<UUID> {

    private UUID id;
    private String name;
    private Integer rowIndex;
    private Integer columnIndex;
    // private UUID parkingId;
}
