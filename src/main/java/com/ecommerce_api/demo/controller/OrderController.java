package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.services.OrderService;
import com.ecommerce_api.demo.services.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){

        this.orderService = orderService;
    }

    @PostMapping
    ResponseEntity<?>createOrder(){

        orderService.saveOrder();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<?>getOrderById(@PathVariable Long id){

        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    @GetMapping
    ResponseEntity<?>getOrdersForTheAuthenticatedUser(){

        return ResponseEntity.ok().body(orderService.getAllOrdersForSpecificUser());
    }

    @GetMapping("/admin/all")
    ResponseEntity<?>getAllOrdersForTheAdmin(){

        return ResponseEntity.ok().body(orderService.getAllOrders());
    }
}
