package org.parkz.modules.parking_session.entity.redis;

import lombok.Data;
import org.parkz.modules.parking.model.ParkingSlotInfo;
import org.parkz.modules.parking_session.enums.ParkingSessionStatus;
import org.parkz.modules.vehicle.model.VehicleInfo;
import org.parkz.shared.domain.RedisPersistable;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Data
@RedisHash
public class ParkingSessionRedisEntity extends RedisPersistable<UUID> {

    private String guestName;
    private String guestPhone;
    private ParkingSessionStatus status;
    private UUID vehicleId;
    private UUID parkingSlotId;
    private VehicleInfo vehicleSnapShot;
    private ParkingSlotInfo parkingSlotSnapShot;
}
