package com.sabah.microservice.product_service.service;


import java.util.List;

import org.springframework.stereotype.Service;
import com.sabah.microservice.product_service.dto.ProductRequest;
import com.sabah.microservice.product_service.dto.ProductResponse;
import com.sabah.microservice.product_service.model.Product;
import com.sabah.microservice.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                            .name(productRequest.name())
                            .description(productRequest.description())
                            .skuCode(productRequest.skuCode())
                            .price(productRequest.price())
                            .build();
        productRepository.save(product);
        log.info("Product was created successfully");
        return new ProductResponse(productRequest.id(), productRequest.name(), productRequest.description(), productRequest.skuCode(),productRequest.price());

    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
        .stream()
        .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),product.getSkuCode(),product.getPrice()))
        .toList();
    }


}
