package com.example.bookshop.securityconfig;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String BEARER_AUTH_HEADER = "BearerAuth";
    private static final String BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(BEARER_AUTH_HEADER,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme(BEARER_AUTH_HEADER)
                                .bearerFormat(BEARER_FORMAT)))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_AUTH_HEADER));
    }
}

