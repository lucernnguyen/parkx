package org.parkz.modules.parking_session.model.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Data
@SuperBuilder
@Jacksonized
public class ConfirmCheckOutRequest extends ConfirmCheckInRequest {


}
