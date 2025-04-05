package com.sabah.microservice.order_service.client;


import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.slf4j.Logger;
import groovy.util.logging.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
// import lombok.extern.slf4j.Slf4j;

//----- Below part was migrated from feign client--- to rest client
//this url is localhost://8082 -- given in app.properties
//there are two app.properties (one specificall for test)
// @FeignClient(value = "inventory" , url = "${inventory.url}") --- migrated 

@Slf4j
public interface InventoryClient{

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    //this is similar to spring-data-jpa/ no need to implement this method
    //this method will be implemented by feign client
    //only return type , request parameters
    //fn name doesnt matter only the value url matters
    //this is synchronous call
    // @RequestMapping(method = RequestMethod.GET, value = "/api/inventory") -- migrated part
    //implementing circuit breaker using annotation here
    @GetExchange("/api/inventory")
    @CircuitBreaker(name = "inventoryClient" , fallbackMethod = "fallbackMethod")
    @Retry(name = "inventoryClient")
    Boolean isInStockS(@RequestParam String skuCode, @RequestParam Integer quantity);


    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable t){
        log.info("Cannot get inventory for skucode{}, failiure reason:{}",skuCode,t.getMessage());
        return false;
    }
}
