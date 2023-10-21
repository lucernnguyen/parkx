package org.parkz.modules.wallet.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class InquiryResponse {

    private String redirectUrl;
}
