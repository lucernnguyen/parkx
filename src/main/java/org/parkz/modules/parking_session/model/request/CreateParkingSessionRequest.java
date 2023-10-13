package org.parkz.modules.parking_session.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateParkingSessionRequest {

    @NotNull
    private UUID vehicleId;
    private UUID parkingSlotId;
}
