package com.ecommerce_api.demo.controller;


import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    ResponseEntity<CartResponseDTO>getCart(){

        return ResponseEntity.ok().body(cartService.getCart());
    }

    @GetMapping("/all")
    ResponseEntity<List<CartResponseDTO>>getAllCarts(){

        return ResponseEntity.ok().body(cartService.getAllCarts());
    }

    @PostMapping
    public ResponseEntity<Void>addCartItem(@RequestBody @Valid CartItemRequestDTO cartItemRequestDTO){

        cartService.addProductToCart(cartItemRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>removeCartItem(@PathVariable Long id){

        cartService.removeProductFromCart(id);
        return ResponseEntity.ok().build();
    }
}
