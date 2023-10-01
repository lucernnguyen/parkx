package org.parkz.modules.vehicle.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.user.entity.UserEntity;
import org.parkz.modules.user.enums.UserErrorCode;
import org.parkz.modules.user.repository.UserRepository;
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
public class AppVehicleFactory extends VehicleFactory {

    private final UserRepository userRepository;

    @Override
    protected VehicleInfo preCreate(VehicleInfo detail) throws InvalidException {
        if (repository.existsByLicensePlate(detail.getLicensePlate())) {
            throw new InvalidException(VehicleErrorCode.VEHICLE_LICENSE_PLATE_ALREADY_EXISTS);
        }
        return detail;
    }

    @Override
    protected void prePersist(VehicleEntity entity) throws InvalidException {
        UserEntity user = userRepository.findById(JwtUtils.getUserIdString())
                .orElseThrow(() -> new InvalidException(UserErrorCode.USER_NOT_FOUND));
        entity.setUser(user);
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
