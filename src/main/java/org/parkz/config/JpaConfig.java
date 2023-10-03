package org.parkz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.fastboot.security.DefaultUserDetail;
import org.springframework.fastboot.security.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "parkxAuditorAware")
public class JpaConfig {

    @Bean(name = "parkxAuditorAware")
    public AuditorAware<String> parkxAuditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof DefaultUserDetail defaultUserDetail) {
                return Optional.ofNullable(JwtUtils.getUserIdString());
            }
            return Optional.empty();
        };
    }
}
