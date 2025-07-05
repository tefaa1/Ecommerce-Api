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
    ResponseEntity<?>getCart(){

        return ResponseEntity.ok().body(cartService.getCart());
    }

    @GetMapping("/admin/all")
    ResponseEntity<?>getAllCarts(){

        return ResponseEntity.ok().body(cartService.getAllCarts());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?>addCartItem(@RequestBody @Valid CartItemRequestDTO cartItemRequestDTO,
                                        @PathVariable Long productId){

        cartService.addProductToCart(cartItemRequestDTO,productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<?>removeCartItem(@PathVariable Long cartItemId){

        cartService.removeProductFromCart(cartItemId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{cartItemId}/{UpOrDown}")
    public ResponseEntity<?>modifyCartItemQuantity(@PathVariable Long cartItemId,
                                                   @PathVariable Boolean UpOrDown){

        cartService.modifyQuantityOfCartItem(cartItemId,UpOrDown);
        return ResponseEntity.ok().build();
    }
}
