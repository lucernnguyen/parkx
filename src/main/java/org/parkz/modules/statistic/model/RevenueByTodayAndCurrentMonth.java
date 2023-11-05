package org.parkz.modules.statistic.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class RevenueByTodayAndCurrentMonth {

    private BigDecimal today;
    private BigDecimal currentMonth;
}
