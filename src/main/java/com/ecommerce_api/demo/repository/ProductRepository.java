package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.cartItems WHERE p.id = :productId")
    Optional<Product> findProductWithCartItems(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.wishlistedBy WHERE p.id = :productId")
    Optional<Product> findProductWithWishlist(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.orderItems WHERE p.id = :productId")
    Optional<Product> findProductWithOrderItems(@Param("productId") Long productId);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.reviews WHERE p.id = :productId")
    Optional<Product> findProductWithReviews(@Param("productId") Long productId);

    @Query("""
    SELECT p FROM Product p
    LEFT JOIN FETCH p.cartItems
    LEFT JOIN FETCH p.wishlistedBy
    LEFT JOIN FETCH p.orderItems
    LEFT JOIN FETCH p.reviews
    WHERE p.id = :productId
    """)
    Optional<Product> findProductWithAllRelations(@Param("productId") Long productId);
} 