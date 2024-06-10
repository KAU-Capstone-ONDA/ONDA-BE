package com.capstone.onda.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
    type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,
    name = "Authorization", description = "Auth Token"
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
            .version("v1.0.0")
            .title("[Capston] ONDA")
            .description("[Capston] ONDA Swagger 명세서 입니다");

        return new OpenAPI()
            .info(info);
    }
}
