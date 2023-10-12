package org.parkz.modules.vehicle.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.factory.IVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.modules.vehicle.repository.VehicleRepository;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class VehicleFactory
        extends BasePersistDataFactory<UUID, VehicleInfo, VehicleInfo, UUID, VehicleEntity, VehicleRepository>
        implements IVehicleFactory {

}
