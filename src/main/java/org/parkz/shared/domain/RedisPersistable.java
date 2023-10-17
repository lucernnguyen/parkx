package org.parkz.shared.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisPersistable<I> implements Serializable {

    @Id
    private I id;

    @TimeToLive
    private long timeToLive;

}
