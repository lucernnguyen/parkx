package org.parkz.modules.parking_session.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Data
@SuperBuilder
@Jacksonized
public class ConfirmCheckOutRequest extends ConfirmCheckInRequest {


}
