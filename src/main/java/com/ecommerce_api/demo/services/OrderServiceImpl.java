package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.Order;
import com.ecommerce_api.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }
    @Override
    public Order saveOrder(Order order) {
        return null;
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        return null;
    }

    @Override
    public Order getOrderById(Long id) {
        return null;
    }

    @Override
    public Order getOrderWithOrderItems(Long id) {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrderById(Long id) {

    }

    @Override
    public void deleteAllOrders() {

    }
}
