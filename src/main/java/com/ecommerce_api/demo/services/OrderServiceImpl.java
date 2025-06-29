package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.response.OrderResponseDTO;
import com.ecommerce_api.demo.model.entity.*;
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

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            CartRepository cartRepository,
                            OrderMapper orderMapper){

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public void saveOrder() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        // We fetch the cart with its items explicitly because the user's cart is lazily loaded and doesn't include items by default.
        Long cartId = user.getCart().getId();
        Cart cart = cartRepository.findCartWithItemsByCartId(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id " + cartId + " not found"));

        Order order = converCartToOrder(cart);
        user.addOrder(order);

        orderRepository.save(order);

    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Order order = orderRepository.findByIdAndUserEmail(id,email)
                .orElseThrow(() -> new AccessDeniedException("You are not allowed to access this resource"));

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderResponseDTO>getAllOrdersForSpecificUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findUserWithOrdersByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

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

    @Override
    public Order converCartToOrder(Cart cart) {
        Order order = new Order();
        BigDecimal sum = BigDecimal.ZERO;
        for(CartItem cartItem:cart.getCartItems()) {

            Product product = cartItem.getProduct();
            BigDecimal curPrice = product.getNewPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            sum = sum.add(curPrice);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .priceAtOrderTime(curPrice)
                    .quantity(cartItem.getQuantity())
                    .build();
            order.addOrderItem(orderItem);
        }
        order.setTotalPrice(sum);
        return order;
    }
}
