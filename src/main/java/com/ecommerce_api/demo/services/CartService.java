package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import java.util.List;

public interface CartService {
    void addProductToCart(CartItemRequestDTO cartItemRequestDTO);
    void removeProductFromCart(Long cartItemId);
    CartResponseDTO getCart();
    List<CartResponseDTO> getAllCarts();
} 