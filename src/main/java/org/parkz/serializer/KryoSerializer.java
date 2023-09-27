package org.parkz.serializer;

import com.esotericsoftware.kryo.Kryo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.QueryKey;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.redisson.client.handler.State;
import org.redisson.codec.Kryo5Codec;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;
import java.util.*;

@Slf4j
@SuppressWarnings({"unused"})
public class KryoSerializer<T> extends Kryo5Codec implements RedisSerializer<T> {

    public KryoSerializer() {
        super();
    }

    public KryoSerializer(Class<T> type) {
        super();
    }

    @Override
    protected Kryo createKryo(ClassLoader classLoader) {
        Kryo kryo = super.createKryo(classLoader);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.register(HashMap.class).setInstantiator(HashMap::new);
        kryo.register(HashSet.class).setInstantiator(HashSet::new);
        kryo.register(LinkedHashSet.class).setInstantiator(LinkedHashSet::new);
        kryo.register(LinkedHashMap.class).setInstantiator(LinkedHashMap::new);
        kryo.register(TreeMap.class).setInstantiator(TreeMap::new);
        kryo.register(ArrayList.class).setInstantiator(ArrayList::new);
        kryo.register(QueryKey.class);
        kryo.register(UUID.class);

        return kryo;
    }

    @Override
    public byte[] serialize(T o) throws SerializationException {
        if (o == null) {
            return new byte[0];
        }
        ByteBuf buf = null;
        try {
            buf = this.getValueEncoder().encode(o);
            return ByteBufUtil.getBytes(buf);
        } catch (IOException e) {
            log.error("Cannot serialize with message {}", e.getMessage());
        } finally {
            if (buf != null) {
                buf.release();
            }
        }
        return new byte[0];
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        }
        ByteBuf buf = Unpooled.wrappedBuffer(bytes);
        try {
            State state = new State();
            this.getValueDecoder().decode(buf, state);
            return state.getValue();
        } catch (IOException e) {
            log.error("Cannot deserialize with message {}", e.getMessage());
        } finally {
            buf.release();
        }
        return null;
    }
}
