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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;

    private final UserService userService;

    private final ProductService productService;

    private final CartItemMapper cartItemMapper;

    private final CartItemService cartItemService;
    @Autowired
    public CartServiceImpl(
            CartRepository cartRepository,
            CartMapper cartMapper,
            UserService userService,
            CartItemMapper cartItemMapper,
            CartItemService cartItemService,
            ProductService productService){

        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
        this.userService = userService;
        this.cartItemMapper = cartItemMapper;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public void addProductToCart(CartItemRequestDTO cartItemRequestDTO, Long productId) {

        Cart cart = userService.getTheCurrentUser().getCart();
        for (CartItem cartItem : cart.getCartItems()) {

            if (productId.equals(cartItem.getProduct().getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This product is already in the cart");
            }
        }

        Product product = productService.getProductEntityById(productId);
        if(product.getStockQuantity() < cartItemRequestDTO.getQuantity()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Requested quantity exceeds available stock. Only " + product.getStockQuantity() + " left in stock."
            );
        }
        cart.addCartItem(cartItemMapper.toEntity(cartItemRequestDTO, productId));

        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(Long cartItemId) {

        Cart cart = userService.getTheCurrentUser().getCart();
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if(!cart.getCartItems().contains(cartItem)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This product is not in your cart");
        }
        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }


    @Override
    public CartResponseDTO getCart() {

        Cart cart = userService.getTheCurrentUser().getCart();

        return cartMapper.toDto(cart);
    }

    @Override
    public List<CartResponseDTO> getAllCarts() {

        List<Cart>carts = cartRepository.findAllCartsWithCartItems()
                .orElseThrow(() -> new ResourceNotFoundException("There is no carts"));

        return carts.stream()
                .map(cartMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Cart findCartWithItemsByCartId(Long id) {

        return cartRepository.findCartWithItemsByCartId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart with Id " + id + " not found"));
    }

    @Override
    public void modifyQuantityOfCartItem(Long id, boolean UpOrDown) {

        Cart cart = userService.getTheCurrentUser().getCart();
        CartItem cartItem = cartItemService.getCartItemById(id);

        if(!cart.getCartItems().contains(cartItem)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        Product product = productService.getProductEntityById(cartItem.getId());

        int nextQuantity = cartItem.getQuantity() + (UpOrDown ? 1 : -1);
        if (nextQuantity > product.getStockQuantity()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add more than available stock");
        }
        if (nextQuantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity cannot be less than 1");
        }

        cartItem.setQuantity(nextQuantity);
        cartItemService.save(cartItem);
    }
}
