package com.gon.store.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "PixelVault API - Videogame Store",
        version = "1.0",
        description = "API REST for inventory control, developers, VIP clients and cash register.",
        contact = @Contact(
            name = "PixelVault Backend Support",
            email = "dev@pixelvault.com"
        )
    ),
    security = @SecurityRequirement(
        name = "bearerAuth"
    )
)
@SecurityScheme(
    name = "bearerAuth",
    description = "Paste your JWT token obtained in /api/auth/login here",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
