package org.parkz.modules.parking.factory.app;

import lombok.RequiredArgsConstructor;
import org.parkz.modules.parking.factory.impl.ParkingSlotFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppParkingSlotFactory extends ParkingSlotFactory implements IAppParkingSlotFactory {
}
