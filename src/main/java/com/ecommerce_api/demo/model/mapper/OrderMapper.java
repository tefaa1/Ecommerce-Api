package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.model.dto.response.UserResponseDTO;
import com.ecommerce_api.demo.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final UserMapper userMapper;

    @Autowired
    public OrderMapper(UserMapper userMapper){

        this.userMapper = userMapper;
    }
    public OrderResponseDTO toDto(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .slimUserDTO(userMapper.toSlimDto(order.getUser()))
                .totalPrice(order.getTotalPrice())
                .createdAt(order.getCreatedAt())
                .orderItemResponseDTOSet(order.getOrderItems().stream()
                        .map(OrderItemMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
    
    // No toEntity method since there's no OrderRequestDTO in the codebase
} 