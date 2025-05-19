package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("select o from Order o LEFT JOIN FETCH Order.orderItems WHERE o.id = :orderId")
    Optional<Order> findOrderWithOrderItemByOrderId(@Param("orderId")Long orderId);
} 