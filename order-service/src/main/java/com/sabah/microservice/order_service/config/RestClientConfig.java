package com.sabah.microservice.order_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.sabah.microservice.order_service.client.InventoryClient;

@Configuration
public class RestClientConfig {

    //u can value to inject from application.properties
    @Value("${inventory.url}")
    private String inventoryServiceUrl;


    //creating a http interface
    @Bean
    InventoryClient inventoryClient(){

        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .build();
        var restclientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restclientAdapter).build();

        return httpServiceProxyFactory.createClient(InventoryClient.class);

    }



}
