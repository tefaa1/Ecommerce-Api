package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.id = :orderId AND o.user.email = :email")
    Optional<Order> findByIdAndUserEmail(@Param("orderId") Long orderId, @Param("email") String email);
} 