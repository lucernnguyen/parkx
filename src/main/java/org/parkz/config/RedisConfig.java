package org.parkz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration(proxyBeanMethods = false)
@EnableRedisRepositories(
        basePackages = {
                "org.parkz.modules.*.repository.redis"
        }
)
public class RedisConfig {

}
