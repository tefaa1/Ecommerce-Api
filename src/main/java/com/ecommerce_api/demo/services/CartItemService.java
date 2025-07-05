package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.CartItem;

public interface CartItemService {

    CartItem getCartItemById(Long id);
    void save(CartItem cartItem);
}
