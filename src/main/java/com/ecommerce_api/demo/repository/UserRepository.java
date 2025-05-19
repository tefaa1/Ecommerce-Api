package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.wishlist WHERE u.id = :userId")
    Optional<User> findUserWithWishlistById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId")
    Optional<User> findUserWithRolesById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    Optional<User> findUserWithOrdersById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.reviews WHERE u.id = :userId")
    Optional<User> findUserWithReviewsById(@Param("userId") Long userId);

    @Query("""
    SELECT u FROM User u
    LEFT JOIN FETCH u.wishlist
    LEFT JOIN FETCH u.roles
    LEFT JOIN FETCH u.orders
    LEFT JOIN FETCH u.reviews
    WHERE u.id = :userId
    """)
    Optional<User> findUserWithAllRelationsById(@Param("userId") Long userId);
} 