package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.entity.CartItem;
import com.ecommerce_api.demo.repository.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService{
    private final CartItemRepository cartItemRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found"));
    }

    @Override
    public void save(CartItem cartItem) {

        cartItemRepository.save(cartItem);
    }
}
