package org.parkz.modules.parking_session.repository.redis;

import org.parkz.modules.parking_session.entity.redis.ParkingSessionRedisEntity;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.UUID;

public interface ParkingSessionRedisRepository extends KeyValueRepository<ParkingSessionRedisEntity, UUID> {
}
