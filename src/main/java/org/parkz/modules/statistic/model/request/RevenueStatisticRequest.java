package org.parkz.modules.statistic.model.request;

import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RevenueStatisticRequest {

    @PastOrPresent
    private LocalDate from;
    @PastOrPresent
    private LocalDate to;

    public LocalDate getFrom() {
        return from == null ? LocalDate.now().minusDays(30) : from;
    }

    public LocalDate getTo() {
        return to == null ? LocalDate.now() : to;
    }
}
