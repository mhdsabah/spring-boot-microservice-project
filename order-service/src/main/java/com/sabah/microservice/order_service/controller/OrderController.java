package com.sabah.microservice.order_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sabah.microservice.order_service.dto.OrderRequest;
import com.sabah.microservice.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        // orderRequest.
        System.out.println("\n\n\n\n" +orderRequest + "\n\n\n\n");
        
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
        
    }




}
