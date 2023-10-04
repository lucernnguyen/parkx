package org.parkz.modules.parking.model;

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

    private UUID id;
    private String name;
    private String description;
    @Builder.Default
    private boolean active = true;
    @Builder.Default
    private boolean fullSlot = true;
}
