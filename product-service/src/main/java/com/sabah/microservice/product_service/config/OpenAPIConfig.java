package com.sabah.microservice.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {


    @Bean
    public OpenAPI productServiceAPI(){
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Product Service API")
                        .description("Product Service API for managing product service")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0"))
                );
    }

}
