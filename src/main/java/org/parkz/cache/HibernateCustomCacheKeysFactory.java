package org.parkz.cache;

import org.parkz.serializer.KryoSerializer;
import org.redisson.client.codec.Codec;
import org.redisson.hibernate.RedissonCacheKeysFactory;

@SuppressWarnings({"unused"})
public class HibernateCustomCacheKeysFactory extends RedissonCacheKeysFactory {

    public HibernateCustomCacheKeysFactory() {
        super(new KryoSerializer<>());
    }

    public HibernateCustomCacheKeysFactory(Codec codec) {
        super(codec);
    }
}
