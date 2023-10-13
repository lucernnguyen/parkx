package org.parkz.modules.parking.factory.impl;

import lombok.RequiredArgsConstructor;
import org.parkz.event.parking_session.VehicleCheckInEvent;
import org.parkz.event.parking_session.VehicleCheckOutEvent;
import org.parkz.modules.parking.entity.ParkingSlotEntity;
import org.parkz.modules.parking.factory.IParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking.repository.ParkingSlotRepository;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.fastboot.rest.common.factory.data.base.BasePersistDataFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParkingSlotFactory
        extends BasePersistDataFactory<UUID, ParkingSlotInfo, ParkingSlotInfo, UUID, ParkingSlotEntity, ParkingSlotRepository>
        implements IParkingSlotFactory {

    @Override
    public ParkingSlotInfo getParkingSlotNullable(UUID id) throws InvalidException {
        if (id == null) {
            return null;
        }
        ParkingSlotEntity parkingSlot = repository.findById(id).orElse(null);
        if (parkingSlot != null) {
            return convertToDetail(parkingSlot);
        }
        return null;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = VehicleCheckInEvent.class)
    public void vehicleCheckInEvent(VehicleCheckInEvent event) {
        setStatusParkingSlot(event.parkingSlotId(), true);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, value = VehicleCheckOutEvent.class)
    protected void vehicleCheckoutEvent(VehicleCheckOutEvent event) {
        setStatusParkingSlot(event.parkingSlotId(), false);
    }

    private void setStatusParkingSlot(UUID id, boolean hasParking) {
        if (id == null) {
            return;
        }
        ParkingSlotEntity parkingSlot = repository.getReferenceById(id);
        parkingSlot.setHasParking(hasParking);
        repository.save(parkingSlot);
    }
}
