package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartItemResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.CartItem;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    private final ProductRepository productRepository;

    private static ProductMapper productMapper;

    public CartItemMapper(
            ProductRepository productRepository,
            ProductMapper productMapper){

        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }
    public static CartItemResponseDTO toDto(CartItem cartItem){
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .productResponseDTO(productMapper.toDto(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemRequestDTO cartItemUpdateRequestDTO){

        Long id = cartItemUpdateRequestDTO.getProductId();
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + id + " not found"));
        return CartItem.builder()
                .quantity(cartItemUpdateRequestDTO.getQuantity())
                .product(product)
                .build();
    }
}
