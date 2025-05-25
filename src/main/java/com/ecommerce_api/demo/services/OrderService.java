package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.Order;
import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    Order updateOrder(Long id, Order order);
    Order getOrderById(Long id);
    Order getOrderWithOrderItems(Long id);
    List<Order> getAllOrders();
    void deleteOrderById(Long id);
    void deleteAllOrders();
} 