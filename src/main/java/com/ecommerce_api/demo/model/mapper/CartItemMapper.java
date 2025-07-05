package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.CartItemRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CartItemResponseDTO;
import com.ecommerce_api.demo.model.entity.Cart;
import com.ecommerce_api.demo.model.entity.CartItem;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.repository.ProductRepository;
import com.ecommerce_api.demo.services.ProductService;
import org.springframework.stereotype.Component;

@Component
public class CartItemMapper {

    private final ProductService productService;

    private static ProductMapper productMapper;

    public CartItemMapper(
            ProductService productService,
            ProductMapper productMapper){

        this.productService = productService;
        this.productMapper = productMapper;
    }
    public static CartItemResponseDTO toDto(CartItem cartItem){
        return CartItemResponseDTO.builder()
                .id(cartItem.getId())
                .slimProductDTO(productMapper.toSlimDto(cartItem.getProduct()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public CartItem toEntity(CartItemRequestDTO cartItemUpdateRequestDTO,Long productId){

        Product product = productService.getProductEntityById(productId);
        return CartItem.builder()
                .quantity(cartItemUpdateRequestDTO.getQuantity())
                .product(product)
                .build();
    }
}
