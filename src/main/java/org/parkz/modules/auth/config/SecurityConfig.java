package org.parkz.modules.auth.config;

import org.parkz.modules.auth.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.fastboot.security.BearerTokenFilter;
import org.springframework.fastboot.security.DefaultAuthentication;
import org.springframework.fastboot.security.configuration.SecurityConfigFactory;
import org.springframework.fastboot.security.properties.SecurityProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class SecurityConfig extends SecurityConfigFactory {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public SecurityConfig(SecurityProperties properties,
                          Converter<Jwt, DefaultAuthentication> jwtAuthenticationConverter,
                          JwtAuthorizationFilter jwtAuthorizationFilter) {
        super(properties, jwtAuthenticationConverter);
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Override
    protected void configureFilter(HttpSecurity http) {
        super.configureFilter(http);
        http.addFilterAfter(jwtAuthorizationFilter, BearerTokenFilter.class);
    }

}
