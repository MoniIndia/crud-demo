package com.example.crud_demo.SwaggerConfiguration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

/**
 * Swagger configuration.
 */
@Configuration
public class SwaggerConfig {
    private static final String BASE_PACKAGE = "com.example.crud_demo";
    private static final String TEAM_NAME = "Target";
    private static final String TEAM_URL = "target.com";
    private static final String TEAM_EMAIL = "contacts@abc.com";
    private static final String APPLICATION_NAME = "Rest API for CRUD Operation";
    private static final String APPLICATION_DESCRIPTION = "API for CRUD Operation";

    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(APPLICATION_NAME)
                .description(APPLICATION_DESCRIPTION)
                .contact(new Contact().name(TEAM_NAME).email(TEAM_EMAIL).url(TEAM_URL));

    }
}