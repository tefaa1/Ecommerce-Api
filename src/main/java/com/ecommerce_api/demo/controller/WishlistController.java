package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final UserService userService;

    @Autowired
    public WishlistController(UserService userService){

        this.userService = userService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?>addProductToMyWishlist(@PathVariable Long productId){

        userService.addProductToMyWishlist(productId);
        return ResponseEntity.ok("Added Successfully");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?>removeProductFromMyWishlist(@PathVariable Long productId){

        userService.removeProductFromMyWishlist(productId);
        return ResponseEntity.ok("Removed Successfully");
    }

    @GetMapping
    public ResponseEntity<?>getMyWishlist(){

        return ResponseEntity.ok().body(userService.getMyWishlist());
    }
}
