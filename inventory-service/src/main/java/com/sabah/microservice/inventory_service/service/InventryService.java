package com.sabah.microservice.inventory_service.service;

import org.springframework.stereotype.Service;

import com.sabah.microservice.inventory_service.repository.InventryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventryService {
    private final InventryRepository inventryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        //find inventry of given skucode where quatity>=0
        return inventryRepository.existsByskuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
        
    }

}
