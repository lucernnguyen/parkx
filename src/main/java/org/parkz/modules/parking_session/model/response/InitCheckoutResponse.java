package org.parkz.modules.parking_session.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.fastboot.rest.common.model.response.SuccessResponse;

import java.util.UUID;

@Data
@NoArgsConstructor
public class InitCheckoutResponse extends SuccessResponse {

    private UUID transactionId;
}
