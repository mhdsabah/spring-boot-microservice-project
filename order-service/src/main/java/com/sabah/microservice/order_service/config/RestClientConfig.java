package com.sabah.microservice.order_service.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import com.sabah.microservice.order_service.client.InventoryClient;

// import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;

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
                .requestFactory(getClientRequestFactory())
                .build();
        var restclientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restclientAdapter).build();
    

        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }

        @SuppressWarnings({ "removal", "deprecation" })
        private ClientHttpRequestFactory getClientRequestFactory(){
            ClientHttpRequestFactorySettings clientHttpRequestFactorySettings =  ClientHttpRequestFactorySettings.DEFAULTS
                      .withConnectTimeout(Duration.ofSeconds(3))
                        .withReadTimeout(Duration.ofSeconds(3));
                        return ClientHttpRequestFactories.get(clientHttpRequestFactorySettings);
        }
       
        

    

    



}
