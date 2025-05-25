package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.Cart;
import java.util.List;

public interface CartService {
    Cart saveCart(Cart cart);
    Cart updateCart(Long id,Cart cart);
    Cart getCartById(Long id);
    Cart getCartWithItems(Long id);
    List<Cart> getAllCarts();
    void deleteCartById(Long id);
    void deleteAllCarts();
} 