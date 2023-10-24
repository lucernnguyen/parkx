package org.parkz.infrastructure.client.paypal.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.parkz.infrastructure.client.paypal.common.Link;
import org.parkz.infrastructure.client.paypal.common.OrderStatus;
import org.parkz.infrastructure.client.paypal.common.PurchaseUnit;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Jacksonized
public class CreateOrderResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private OrderStatus status;
    @JsonProperty("create_time")
    private LocalDateTime createTime;
    @JsonProperty("links")
    private List<Link> links;
    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @JsonIgnore
    public String getApproveLink() {
        if (CollectionUtils.isEmpty(links)) {
            return null;
        }
        return this.links.stream()
                .filter(l -> "approve".equals(l.getRel()))
                .map(Link::getHref)
                .findFirst()
                .orElse(null);
    }
}
