package com.sabah.microservice.order_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sabah.microservice.order_service.dto.OrderRequest;
import com.sabah.microservice.order_service.model.Order;
import com.sabah.microservice.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());
        // System.out.println(order.getId() + "," + order.getSkuCode() + "," + order.getQuantity());
        
        orderRepository.save(order);
    }

}
