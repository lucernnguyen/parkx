package org.parkz.modules.parking_session.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.parkz.modules.parking_session.enums.PaymentType;

import java.util.UUID;

@Data
public class InitCheckoutRequest {

    @NotNull
    private UUID sessionId;
    @NotNull
    private PaymentType paymentType;
}
