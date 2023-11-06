package org.parkz.modules.statistic.factory.system;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.parkz.modules.statistic.model.ParkingSessionByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueChart;
import org.parkz.modules.statistic.model.VehicleChart;
import org.parkz.modules.statistic.model.request.RevenueStatisticRequest;
import org.parkz.modules.wallet.enums.TransactionType;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings({"unchecked"})
public class SystemStatisticFactory implements ISystemStatisticFactory {

    private final EntityManager entityManager;

    @Override
    public List<VehicleChart> vehicleStatistic() {
        return entityManager
                .createNamedQuery("vehicleStatistic")
                .getResultList();
    }

    @Override
    public RevenueByTodayAndCurrentMonth revenueByTodayAndCurrentMonth() throws InvalidException {
        LocalDate today = LocalDate.now();
        BigDecimal revenueToday = (BigDecimal) entityManager.createNativeQuery("""
                        SELECT COALESCE(SUM(t.amount), 0.0)
                        FROM parkx_transaction t
                        WHERE
                            t.transaction_type = 'PAYMENT'
                            AND t.created_date >= :from
                            AND t.created_date <= :to
                        """)
                .setParameter("from", today.atStartOfDay())
                .setParameter("to", today.atTime(23, 59, 59))
                .getSingleResult();
        BigDecimal revenueThisMonth = (BigDecimal) entityManager.createNativeQuery("""
                        SELECT COALESCE(SUM(t.amount), 0.0)
                                FROM parkx_transaction t
                                WHERE
                                    t.transaction_type = 'PAYMENT'
                                    AND t.created_date >= :from
                                    AND t.created_date <= :to
                        """)
                .setParameter("from", YearMonth.from(today).atDay(1).atStartOfDay())
                .setParameter("to", YearMonth.from(today).atEndOfMonth().atTime(LocalTime.MAX))
                .getSingleResult();
        return RevenueByTodayAndCurrentMonth.builder()
                .today(revenueToday)
                .currentMonth(revenueThisMonth)
                .build();
    }

    public List<RevenueChart> revenueByDateChart(RevenueStatisticRequest request) {
        return entityManager.createNamedQuery("revenueByDate")
                .setParameter("transactionType", TransactionType.PAYMENT.name())
                .setParameter("from", request.getFrom().atStartOfDay())
                .setParameter("to", request.getTo().atTime(LocalTime.MAX))
                .getResultList();
    }

    @Override
    public ParkingSessionByTodayAndCurrentMonth parkingSessionByTodayAndCurrentMonthCard() throws InvalidException {
        LocalDate today = LocalDate.now();
        var sessionToday = (ParkingSessionByTodayAndCurrentMonth.SessionStatus) entityManager
                .createNamedQuery("parkingSessionByTime")
                .setParameter("from", today.atStartOfDay())
                .setParameter("to", today.atTime(23, 59, 59))
                .getSingleResult();
        var sessionCurrentMonth = (ParkingSessionByTodayAndCurrentMonth.SessionStatus) entityManager
                .createNamedQuery("parkingSessionByTime")
                .setParameter("from", YearMonth.from(today).atDay(1).atStartOfDay())
                .setParameter("to", YearMonth.from(today).atEndOfMonth().atTime(LocalTime.MAX))
                .getSingleResult();
        return ParkingSessionByTodayAndCurrentMonth.builder()
                .today(sessionToday)
                .currentMonth(sessionCurrentMonth)
                .build();
    }
}
