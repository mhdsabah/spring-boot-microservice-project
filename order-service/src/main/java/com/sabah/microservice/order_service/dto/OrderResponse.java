package com.sabah.microservice.order_service.dto;

import java.math.BigDecimal;

public record OrderResponse(Long id,String orderNumber, String skucode, BigDecimal price, Integer quantity) {


}
