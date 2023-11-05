package org.parkz.modules.statistic.factory.system;

import org.parkz.modules.statistic.model.ParkingSessionByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueChart;
import org.parkz.modules.statistic.model.VehicleChart;
import org.parkz.modules.statistic.model.request.RevenueStatisticRequest;
import org.springframework.fastboot.exception.InvalidException;

import java.util.List;

public interface ISystemStatisticFactory {

    List<VehicleChart> vehicleStatistic();

    RevenueByTodayAndCurrentMonth revenueByTodayAndCurrentMonth() throws InvalidException;

    List<RevenueChart> revenueByDateChart(RevenueStatisticRequest request) throws InvalidException;

    ParkingSessionByTodayAndCurrentMonth parkingSessionByTodayAndCurrentMonthCard() throws InvalidException;
}
