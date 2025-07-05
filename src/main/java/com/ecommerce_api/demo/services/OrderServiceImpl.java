package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.model.entity.*;
import com.ecommerce_api.demo.model.mapper.CartMapper;
import com.ecommerce_api.demo.model.mapper.OrderMapper;
import com.ecommerce_api.demo.repository.CartRepository;
import com.ecommerce_api.demo.repository.OrderRepository;
import com.ecommerce_api.demo.repository.ProductRepository;
import com.ecommerce_api.demo.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final CartService cartService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService,
                            CartService cartService,
                            OrderMapper orderMapper){

        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cartService = cartService;
        this.orderMapper = orderMapper;
    }

    @Override
    public void saveOrder() {

        User user = userService.getTheCurrentUser();

        // We fetch the cart with its items explicitly because the user's cart is lazily loaded and doesn't include items by default.
        Long cartId = user.getCart().getId();
        Cart cart = cartService.findCartWithItemsByCartId(cartId);

        Order order = CartMapper.converCartToOrder(cart);
        user.addOrder(order);

        orderRepository.save(order);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {

        String email = userService.getTheCurrentUserEmail();

        Order order = orderRepository.findByIdAndUserEmail(id,email)
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to access this resource"));

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDTO>getAllOrdersForSpecificUser() {

        User user = userService.getTheCurrentUser();

        return user.getOrders().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
