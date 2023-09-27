package org.parkz.infrastructure.firebase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.firebase")
public class FirebaseConfigurationProperties {

    private String configPath;
}
