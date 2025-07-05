package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.model.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponseDTO toDto(Cart cart){
        return CartResponseDTO.builder()
                .id(cart.getId())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .cartItemResponseDTOSet(cart.getCartItems().stream()
                        .map(CartItemMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public static Order converCartToOrder(Cart cart) {
        Order order = new Order();
        BigDecimal sum = BigDecimal.ZERO;
        for(CartItem cartItem:cart.getCartItems()) {

            Product product = cartItem.getProduct();
            BigDecimal curPrice = product.getNewPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            sum = sum.add(curPrice);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .priceAtOrderTime(curPrice)
                    .quantity(cartItem.getQuantity())
                    .build();
            order.addOrderItem(orderItem);
        }
        order.setTotalPrice(sum);
        return order;
    }

    // There is no toEntity method because the update cart request only contains a single CartItemUpdateRequestDTO.
    // Adding more than one item to the cart at the same time is not supported.
}
