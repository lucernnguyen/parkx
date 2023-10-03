package org.parkz.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Data
@Configuration(proxyBeanMethods = false)
public class JacksonConfig {

    @Bean
    public SimpleModule stringTrimModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(String.class, new StringDeserializer() {
            @Override
            public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
                return super.deserialize(p, context).trim();
            }
        });
        return simpleModule;
    }
}
