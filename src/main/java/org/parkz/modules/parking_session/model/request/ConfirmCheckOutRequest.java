package org.parkz.modules.parking_session.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.URL;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class ConfirmCheckOutRequest {

    @NotNull
    private UUID sessionId;
    @Builder.Default
    private boolean allow = false;
    @Size(min = 1, max = 10)
    private List<@URL String> checkOutCapture;
}
