package org.parkz.shared.event.parking_session;

import java.util.UUID;

public record PaymentBeforeCheckOutEvent(
        UUID parkingSessionId
) {
}
