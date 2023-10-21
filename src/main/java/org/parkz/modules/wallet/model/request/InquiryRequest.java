package org.parkz.modules.wallet.model.request;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InquiryRequest {

    @DecimalMin(value = "10000")
    private BigDecimal amount;
}
