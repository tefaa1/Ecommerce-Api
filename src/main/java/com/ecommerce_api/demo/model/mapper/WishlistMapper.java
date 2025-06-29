package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.WishlistResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    private final ProductMapper productMapper;

    @Autowired
    public WishlistMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    public WishlistResponseDTO toDto(Set<Product> wishlistProducts) {
        return WishlistResponseDTO.builder()
                .productResponseDTOSet(wishlistProducts.stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
    
    // No toEntity method since wishlist is just a collection of products
} 