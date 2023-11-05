package org.parkz.modules.statistic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class ParkingSessionByTodayAndCurrentMonth {

    private SessionStatus today;
    private SessionStatus currentMonth;

    @Data
    @AllArgsConstructor
    public static class SessionStatus {
        private int checkedIn;
        private int checkedOut;
    }
}
