package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartMapper {

    public static CartResponseDTO toDto(Cart cart){
        return CartResponseDTO.builder()
                .id(cart.getId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .cartItemResponseDTOSet(cart.getCartItems().stream()
                        .map(CartItemMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    // There is no toEntity method because the update cart request only contains a single CartItemUpdateRequestDTO.
    // Adding more than one item to the cart at the same time is not supported.
}
