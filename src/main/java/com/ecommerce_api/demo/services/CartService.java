package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import java.util.List;

public interface CartService {
    void addProductToCart(CartItemRequestDTO cartItemRequestDTO,Long productId);
    void removeProductFromCart(Long cartItemId);
    CartResponseDTO getCart();
    List<CartResponseDTO> getAllCarts();
    Cart findCartWithItemsByCartId(Long id);
    void modifyQuantityOfCartItem(Long id, boolean UpOrDown);
} 