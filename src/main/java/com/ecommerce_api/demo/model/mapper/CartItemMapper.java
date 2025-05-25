package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.CartItemUpdateRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartItemResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.CartItem;
import com.ecommerce_api.demo.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {
    public static CartItemResponseDTO toDto(CartItem cartItem){
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static CartItem toEntity(CartItemUpdateRequestDTO cartItemUpdateRequestDTO, Product product, Cart cart){
        return CartItem.builder()
                .quantity(cartItemUpdateRequestDTO.getQuantity())
                .product(product)
                .cart(cart)
                .build();
    }
}
