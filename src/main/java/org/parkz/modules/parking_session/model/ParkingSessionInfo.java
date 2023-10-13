package org.parkz.modules.parking_session.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.rest.common.model.IBaseData;
import org.springframework.fastboot.rest.serializer.json.View;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class ParkingSessionInfo implements IBaseData<UUID> {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime checkInTime;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime checkOutTime;
    private String guestName;
    private String guestPhone;
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private UUID vehicleId;
    @JsonView({View.Include.Create.class, View.Exclude.Update.class})
    private UUID parkingSlotId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private VehicleInfo vehicleSnapShot;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ParkingSlotInfo parkingSlotSnapShot;
}
