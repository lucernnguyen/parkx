package org.parkz.modules.auth.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "common.security.path-matcher")
public class IgnoreAuthorizationProperties {

    @JsonProperty("ignore-authorization")
    private List<String> pattern = new LinkedList<>();
}
