package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.email = :email")
    Optional<User> findUserWithOrdersByEmail(@Param("email") String email);

//    @Query("SELECT COUNT(u) FROM User u JOIN u.wishlist w WHERE w.id = :productId")
//    long countUsersWithProductInWishlist(@Param("productId") Long productId);
}