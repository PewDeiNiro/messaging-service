package com.pewde.messagingservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Messaging Service",
                description = "Сервис обмена сообщениями",
                version = "1.0.0"
        )
)
public class SwaggerConfig {
}
