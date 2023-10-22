package org.parkz.infrastructure.client.paypal.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.jackson.Jacksonized;
import org.parkz.infrastructure.client.paypal.common.OrderIntent;
import org.parkz.infrastructure.client.paypal.common.PayPalAppContext;
import org.parkz.infrastructure.client.paypal.common.PurchaseUnit;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CreateOrderRequest {

    @JsonProperty("intent")
    private OrderIntent intent;

    @JsonProperty("purchase_units")
    @Singular
    private List<PurchaseUnit> purchaseUnits;

    @JsonProperty("application_context")
    private PayPalAppContext applicationContext;
}
