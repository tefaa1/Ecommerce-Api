package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.id = :cartId")
    Optional<Cart>findCartWithItemsByCartId(@Param("cartId")Long cartId);
} 