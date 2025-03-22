package com.sabah.microservice.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.function.HandlerFunction;
// import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;


//inside this class we have to define all the routing routes for our microservices
@Configuration
public class Routes {

    //the Handler function will forward the request to the url http://localhost:8080
    //there are other methods also inside RequestPredicate etc..

    @Bean
    public RouterFunction<ServerResponse> productServiceRoute(){
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/product") , HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute(){
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order") , HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute(){
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory") , HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    //what the filter will do is it will replace the 
    ///aggregate/product-service/v3/api-docs with /api-docs
    /// so basically http

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("product-service-swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs") , HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/api-docs"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("order-service-swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs") , HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/api-docs"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoute(){
        return GatewayRouterFunctions.route("inventory-service-swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs") , HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/api-docs"))
                .build();
    }

}
