package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.OrderItemResponseDTO;
import com.ecommerce_api.demo.model.entity.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public static OrderItemResponseDTO toDto(OrderItem orderItem) {
        return OrderItemResponseDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .priceAtOrderTime(orderItem.getPriceAtOrderTime())
                .build();
    }
    
    // No toEntity method since there's no OrderItemRequestDTO in the codebase
} 