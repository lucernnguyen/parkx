package org.parkz.modules.vehicle.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.vehicle.entity.VehicleTypeEntity;
import org.parkz.modules.vehicle.factory.IVehicleTypeFactory;
import org.parkz.modules.vehicle.model.VehicleTypeDetails;
import org.parkz.modules.vehicle.model.VehicleTypeInfo;
import org.parkz.modules.vehicle.repository.VehicleTypeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class VehicleTypeFactory
        extends BasePersistDataFactory<Integer, VehicleTypeInfo, VehicleTypeDetails, Integer, VehicleTypeEntity, VehicleTypeRepository>
        implements IVehicleTypeFactory {

}
