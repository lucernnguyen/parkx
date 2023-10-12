package org.parkz.modules.vehicle.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.enums.VehicleErrorCode;
import org.parkz.modules.vehicle.enums.VehicleTypeErrorCode;
import org.parkz.modules.vehicle.factory.impl.VehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.modules.vehicle.repository.VehicleTypeRepository;
import org.springframework.fastboot.exception.ErrorCode;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppVehicleFactory extends VehicleFactory implements IAppVehicleFactory {

    private final VehicleTypeRepository vehicleTypeRepository;

    @Override
    protected VehicleInfo preCreate(VehicleInfo detail) throws InvalidException {
        if (repository.existsByLicensePlate(detail.getLicensePlate())) {
            throw new InvalidException(VehicleErrorCode.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS);
        }
        if (!vehicleTypeRepository.existsById(detail.getVehicleTypeId())) {
            throw new InvalidException(VehicleTypeErrorCode.VEHICLE_TYPE_NOT_FOUND);
        }
        return detail;
    }

    @Override
    protected List<VehicleInfo> aroundGetList() throws InvalidException {
        try {
            return convertList(getRepository().findByUserId(JwtUtils.getUserIdString()));
        } catch (Exception e) {
            throw new InvalidException(ErrorCode.SERVER_ERROR, e);
        }
    }
}
