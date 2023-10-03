package org.parkz.infrastructure.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "Authorization",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "Bearer [token]",
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER,
                description = "Access token"
        ),
        @SecurityScheme(
                name = "x-api-key",
                type = SecuritySchemeType.APIKEY,
                in = SecuritySchemeIn.HEADER
        )
})
@OpenAPIDefinition(
        info = @Info(title = "Parkx API", version = "1.0.0", description = "Documentation Parkx v1.0.0"),
        security = @SecurityRequirement(name = "Authorization")
)
public class OpenApiConfig {
}
