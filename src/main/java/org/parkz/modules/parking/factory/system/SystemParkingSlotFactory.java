package org.parkz.modules.parking.factory.system;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.enums.ParkingSlotErrorCode;
import org.parkz.modules.parking.factory.impl.ParkingSlotFactory;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.springframework.fastboot.exception.InvalidException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemParkingSlotFactory extends ParkingSlotFactory implements ISystemParkingSlotFactory {

    @Override
    protected ParkingSlotInfo preCreate(ParkingSlotInfo detail) throws InvalidException {
        if (repository.existsByRowIndexAndColumnIndex(detail.getRowIndex(), detail.getColumnIndex())) {
            throw new InvalidException(ParkingSlotErrorCode.PARKING_SLOT_POSITION_ALREADY_EXISTS);
        }
        return detail;
    }

    @Override
    protected ParkingSlotInfo preUpdate(UUID id, ParkingSlotInfo detail) throws InvalidException {
        if (id == null) {
            throw new InvalidException(notFound());
        }
        if (repository.existsByRowIndexAndColumnIndexAndIdNot(detail.getRowIndex(), detail.getColumnIndex(), id)) {
            throw new InvalidException(ParkingSlotErrorCode.PARKING_SLOT_POSITION_ALREADY_EXISTS);
        }
        return detail;
    }
}
