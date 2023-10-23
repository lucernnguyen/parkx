package org.parkz.shared.event.parking_session;

import java.math.BigDecimal;
import java.util.UUID;

public record InitCheckOutEvent(
        String userId,
        UUID parkingSessionId,
        BigDecimal vehicleTypePrice
) {
}
