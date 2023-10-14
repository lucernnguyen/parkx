package org.parkz.modules.vehicle.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.event.parking_session.VehicleCheckInEvent;
import org.parkz.event.parking_session.VehicleCheckOutEvent;
import org.parkz.modules.vehicle.entity.VehicleEntity;
import org.parkz.modules.vehicle.enums.VehicleErrorCode;
import org.parkz.modules.vehicle.factory.IVehicleFactory;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.modules.vehicle.repository.VehicleRepository;
import org.springframework.fastboot.exception.IErrorCode;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleFactory
        extends BasePersistDataFactory<UUID, VehicleInfo, VehicleInfo, UUID, VehicleEntity, VehicleRepository>
        implements IVehicleFactory {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = VehicleCheckInEvent.class)
    protected void vehicleCheckInListener(VehicleCheckInEvent event) {
        setStatusVehicle(event.vehicleId(), true);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = VehicleCheckOutEvent.class)
    protected void vehicleCheckoutListener(VehicleCheckOutEvent event) {
        setStatusVehicle(event.vehicleId(), false);
    }

    private void setStatusVehicle(UUID id, boolean checkIn) {
        VehicleEntity vehicle = repository.getReferenceById(id);
        vehicle.setCheckin(checkIn);
        repository.save(vehicle);
    }

    @Override
    protected IErrorCode notFound() {
        return VehicleErrorCode.VEHICLE_NOT_FOUND;
    }
}
