package com.sabah.microservice.order_service.client;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//----- Below part was migrated from feign client--- to rest client
//this url is localhost://8082 -- given in app.properties
//there are two app.properties (one specificall for test)
// @FeignClient(value = "inventory" , url = "${inventory.url}") --- migrated 


public interface InventoryClient{


    //this is similar to spring-data-jpa/ no need to implement this method
    //this method will be implemented by feign client
    //only return type , request parameters
    //fn name doesnt matter only the value url matters
    //this is synchronous call
    // @RequestMapping(method = RequestMethod.GET, value = "/api/inventory") -- migrated part
    @GetExchange("/api/inventory")
    Boolean isInStockS(@RequestParam String skuCode, @RequestParam Integer quantity);
}
