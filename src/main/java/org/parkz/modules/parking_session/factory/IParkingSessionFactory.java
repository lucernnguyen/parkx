package org.parkz.modules.parking_session.factory;

import org.parkz.modules.parking_session.model.ParkingSessionInfo;
import org.springframework.fastboot.rest.common.factory.data.IDataFactory;

import java.util.UUID;

public interface IParkingSessionFactory extends IDataFactory<UUID, ParkingSessionInfo, ParkingSessionInfo> {
}
