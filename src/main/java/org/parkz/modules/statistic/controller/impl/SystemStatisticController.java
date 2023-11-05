package org.parkz.modules.statistic.controller.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.statistic.controller.ISystemStatisticController;
import org.parkz.modules.statistic.factory.system.ISystemStatisticFactory;
import org.parkz.modules.statistic.model.ParkingSessionByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueByTodayAndCurrentMonth;
import org.parkz.modules.statistic.model.RevenueChart;
import org.parkz.modules.statistic.model.VehicleChart;
import org.parkz.modules.statistic.model.request.RevenueStatisticRequest;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.exception.RestException;
import org.springframework.fastboot.rest.common.factory.response.IResponseFactory;
import org.springframework.fastboot.rest.common.model.response.BaseResponse;
import org.springframework.fastboot.util.function.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SystemStatisticController implements ISystemStatisticController {

    private final IResponseFactory responseFactory;
    private final ISystemStatisticFactory systemStatisticFactory;

    @Override
    public ResponseEntity<BaseResponse<List<VehicleChart>>> vehicleAmountByTypeChart() {
        return wrapResponse(systemStatisticFactory::vehicleStatistic);
    }

    @Override
    public ResponseEntity<BaseResponse<RevenueByTodayAndCurrentMonth>> revenueByTodayAndCurrentMonthCard() {
        return wrapResponse(systemStatisticFactory::revenueByTodayAndCurrentMonth);
    }

    @Override
    public ResponseEntity<BaseResponse<List<RevenueChart>>> revenueByDateChart(RevenueStatisticRequest request) {
        return wrapResponse(() -> systemStatisticFactory.revenueByDateChart(request));
    }

    @Override
    public ResponseEntity<BaseResponse<ParkingSessionByTodayAndCurrentMonth>> parkingSessionByTodayAndCurrentMonthCard() {
        return wrapResponse(systemStatisticFactory::parkingSessionByTodayAndCurrentMonthCard);
    }

    protected <R> ResponseEntity<BaseResponse<R>> wrapResponse(Supplier<R> supplier) {
        try {
            R result = supplier.get();
            return responseFactory.success(result);
        } catch (InvalidException e) {
            throw RestException.create(e);
        }
    }
}
