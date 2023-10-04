package org.parkz.modules.vehicle.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.enums.VehicleErrorCode;
import org.parkz.modules.vehicle.factory.impl.VehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppVehicleFactory extends VehicleFactory implements IAppVehicleFactory {

    @Override
    protected VehicleInfo preCreate(VehicleInfo detail) throws InvalidException {
        if (repository.existsByLicensePlate(detail.getLicensePlate())) {
            throw new InvalidException(VehicleErrorCode.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS);
        }
        return detail;
    }

    @Override
    protected Iterable<VehicleEntity> listQuery() throws InvalidException {
        try {
            return getRepository().findByUserId(JwtUtils.getUserIdString());
        } catch (Exception e) {
            throw new InvalidException(ErrorCode.SERVER_ERROR, e);
        }
    }
}
