package com.sabah.microservice.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sabah.microservice.inventory_service.model.Inventry;

public interface InventryRepository extends JpaRepository<Inventry, Long> {
    // Inventry findBySkuCode(String skuCode);
    boolean existsByskuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);

}
