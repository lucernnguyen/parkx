package org.parkz.modules.statistic.model.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueStatisticRequest {

    @PastOrPresent
    @JsonSetter(nulls = Nulls.SKIP)
    private LocalDate from = LocalDate.now().minusDays(30);
    @PastOrPresent
    @JsonSetter(nulls = Nulls.SKIP)
    private LocalDate to = LocalDate.now();
}
