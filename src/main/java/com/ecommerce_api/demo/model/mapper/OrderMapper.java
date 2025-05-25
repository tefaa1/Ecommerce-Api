package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.model.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static OrderResponseDTO toDto(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .user_id(order.getUser().getId())
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .orderItemResponseDTOSet(order.getOrderItems().stream()
                        .map(OrderItemMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
    
    // No toEntity method since there's no OrderRequestDTO in the codebase
} 