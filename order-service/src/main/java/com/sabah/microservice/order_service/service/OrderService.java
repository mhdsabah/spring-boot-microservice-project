package com.sabah.microservice.order_service.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sabah.microservice.order_service.client.InventoryClient;
import com.sabah.microservice.order_service.dto.OrderRequest;
import com.sabah.microservice.order_service.event.OrderPlacedEvent;
import com.sabah.microservice.order_service.model.Order;
import com.sabah.microservice.order_service.repository.OrderRepository;


// import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    // private final CircuitBreaker circuitBreaker;

    public void placeOrder(OrderRequest orderRequest){
        
        var isProductInStock = (inventoryClient.isInStockS(orderRequest.skuCode(), orderRequest.quantity()));
        if (isProductInStock){
            // System.out.println("\n\n\n in stock \n\n\n");
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            // System.out.pr}intln(order.getId() + "," + order.getSkuCode() + "," + order.getQuantity());
            orderRepository.save(order);

            // sendign the message to kafka Topic
            //ordernumber ,email
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            
            // //since we created the template as String, OrderPlacedEvent
            // // we need to convert the OrderPlacedEvent to String using the ObjectMapper

            log.info("Start - sending OrderPlacedEvent {} to kafka topic order_placced", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End - sending OrderPlacedEvent {} to kafka topic order_placced", orderPlacedEvent);
            

        }
        else throw new RuntimeException("Product "+orderRequest.skuCode() +" is out of stock");
    }

}
