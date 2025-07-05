package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    void saveOrder();
    OrderResponseDTO getOrderById(Long id);
    List<OrderResponseDTO> getAllOrdersForSpecificUser();
    List<OrderResponseDTO> getAllOrders();
} 