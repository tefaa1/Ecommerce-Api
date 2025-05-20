package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Cart;
import com.ecommerce_api.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository){
        this.cartRepository=cartRepository;
    }

    @Override
    public Cart saveCart(Cart cart) {
        return null;
    }

    @Override
    public Cart updateCart(Long id, Cart cart) {
        return null;
    }

    @Override
    public Cart getCartById(Long id) {
        return null;
    }

    @Override
    public Cart getCartWithItems(Long id) {
        return null;
    }

    @Override
    public List<Cart> getAllCarts() {
        return null;
    }

    @Override
    public void deleteCartById(Long id) {

    }

    @Override
    public void deleteAllCarts() {

    }
}
