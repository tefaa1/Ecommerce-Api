package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.OrderItemResponseDTO;
import com.ecommerce_api.demo.model.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    private static ProductMapper productMapper;

    @Autowired
    public OrderItemMapper(ProductMapper productMapper){

        this.productMapper = productMapper;
    }

    public static OrderItemResponseDTO toDto(OrderItem orderItem) {
        return OrderItemResponseDTO.builder()
                .id(orderItem.getId())
                .productResponseDTO(productMapper.toDto(orderItem.getProduct()))
                .quantity(orderItem.getQuantity())
                .priceAtOrderTime(orderItem.getPriceAtOrderTime())
                .build();
    }
    
    // No toEntity method since there's no OrderItemRequestDTO in the codebase
} 