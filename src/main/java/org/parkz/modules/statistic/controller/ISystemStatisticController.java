package org.parkz.modules.statistic.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.parkz.modules.statistic.model.ParkingSessionByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueChart;
import org.parkz.modules.statistic.model.VehicleChart;
import org.parkz.modules.statistic.model.request.RevenueStatisticRequest;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "System Statistic Controller")
@RequestMapping("/api/v1/system/statistic")
public interface ISystemStatisticController {

    @GetMapping("/vehicle/chart/amountByType")
    ResponseEntity<BaseResponse<List<VehicleChart>>> vehicleAmountByTypeChart();

    @GetMapping("/revenue/card/todayAndCurrentMonth")
    ResponseEntity<BaseResponse<RevenueByTodayAndCurrentMonth>> revenueByTodayAndCurrentMonthCard();

    @GetMapping("/revenue/chart/byDate")
    ResponseEntity<BaseResponse<List<RevenueChart>>> revenueByDateChart(@Valid RevenueStatisticRequest request);

    @GetMapping("/parkingSession/card/todayAndCurrentMonth")
    ResponseEntity<BaseResponse<ParkingSessionByTodayAndCurrentMonth>> parkingSessionByTodayAndCurrentMonthCard();
}
