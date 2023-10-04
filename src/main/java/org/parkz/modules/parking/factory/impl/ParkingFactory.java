package org.parkz.modules.parking.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.entity.ParkingEntity;
import org.parkz.modules.parking.factory.IParkingFactory;
import org.parkz.modules.parking.model.ParkingInfo;
import org.parkz.modules.parking.repository.ParkingRepository;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class ParkingFactory
        extends BasePersistDataFactory<UUID, ParkingInfo, ParkingInfo, UUID, ParkingEntity, ParkingRepository>
        implements IParkingFactory {

    @Override
    public ParkingInfo getDetailByContext() throws InvalidException {
        ParkingEntity entity = repository.findAll().stream().findFirst()
                .orElseThrow(() -> new InvalidException(notFound()));
        return convertToDetail(entity);
    }
}
