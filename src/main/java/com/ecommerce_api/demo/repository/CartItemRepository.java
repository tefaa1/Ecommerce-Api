package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    // ;)
}
