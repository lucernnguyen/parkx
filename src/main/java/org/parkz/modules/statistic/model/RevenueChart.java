package org.parkz.modules.statistic.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RevenueChart {

    private LocalDate date;
    private BigDecimal amount;
}
