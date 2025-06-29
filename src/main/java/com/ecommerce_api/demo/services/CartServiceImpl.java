package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.CartItem;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.model.mapper.CartItemMapper;
import com.ecommerce_api.demo.model.mapper.CartMapper;
import com.ecommerce_api.demo.repository.CartItemRepository;
import com.ecommerce_api.demo.repository.CartRepository;
import com.ecommerce_api.demo.repository.ProductRepository;
import com.ecommerce_api.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final UserRepository userRepository;

    private final CartItemMapper cartItemMapper;

    private final CartItemRepository cartItemRepository;
    @Autowired
    public CartServiceImpl(
            CartRepository cartRepository,
            CartMapper cartMapper,
            UserRepository userRepository,
            CartItemMapper cartItemMapper,
            CartItemRepository cartItemRepository){

        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
        this.cartItemMapper = cartItemMapper;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void addProductToCart(CartItemRequestDTO cartItemRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        Cart cart = user.getCart();
        cart.addCartItem(cartItemMapper.toEntity(cartItemRequestDTO));

        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long cartItemId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        Cart cart = user.getCart();
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new ResourceNotFoundException("CartItem with Id " + cartItemId + " not found"));
        cart.removeCartItem(cartItem);

        cartRepository.save(cart);
    }


    @Override
    public CartResponseDTO getCart() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Email " + email + " not found"));

        Cart cart = user.getCart();
        return cartMapper.toDto(cart);
    }

    @Override
    public List<CartResponseDTO> getAllCarts() {

        List<Cart>carts = cartRepository.findAllCartsWithCartItems()
                .orElseThrow(() -> new ResourceNotFoundException("There is no carts"));

        return carts.stream().map(cartMapper::toDto).collect(Collectors.toList());
    }
}
