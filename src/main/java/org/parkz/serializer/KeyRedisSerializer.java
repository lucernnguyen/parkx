package org.parkz.serializer;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.NameMapper;

@SuppressWarnings({"unused"})
public class KeyRedisSerializer implements NameMapper {

    private static final String KEY_PREFIX;

    static {
        String prefix = System.getenv("SPRING_CACHE_REDIS_KEY_PREFIX");
        if (StringUtils.isBlank(prefix)) {
            prefix = "PARKX_";
        }
        KEY_PREFIX = prefix;
    }

    @Override
    public String map(String name) {
        return KEY_PREFIX + name;
    }

    @Override
    public String unmap(String name) {
        if (StringUtils.isBlank(name)) {
            return name;
        }
        return name.substring(KEY_PREFIX.length() - 1);
    }
}
