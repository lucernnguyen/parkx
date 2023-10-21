package org.parkz.cache.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.parkz.infrastructure.client.paypal.common.Money;
import org.parkz.shared.enums.CurrencyCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@ToString
@RedisHash
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    @Id
    @JsonProperty("base_code")
    private CurrencyCode baseCode;
    @TimeToLive
    private long ttl;
    @JsonProperty("rates")
    private Map<String, Double> rates;
    @JsonProperty("time_next_update_unix")
    private long timeNextUpdateUnix = 0;

    @JsonProperty(value = "time_next_update_unix")
    public void setTimeNextUpdateUnit(Long unit) {
        this.ttl = unit + 60L - Duration.ofMillis(System.currentTimeMillis()).toSeconds();
    }

    public double getRate(CurrencyCode code) {
        Double rate = this.rates.get(code.name());
        if (rate == null) {
            throw new IllegalArgumentException();
        }
        return rate;
    }

    public Money convert(Money money, CurrencyCode toCode) {
        double fromRate = getRate(money.getCurrencyCode());
        double toRate = getRate(toCode);
        double moneyValue = (toRate * money.getValue()) / fromRate;
        return new Money(moneyValue, toCode);
    }
}
