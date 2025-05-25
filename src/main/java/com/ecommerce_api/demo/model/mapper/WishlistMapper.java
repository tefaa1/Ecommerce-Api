package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.response.WishlistResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WishlistMapper {

    public static WishlistResponseDTO toDto(Set<Product> wishlistProducts) {
        return WishlistResponseDTO.builder()
                .productResponseDTOSet(wishlistProducts.stream()
                        .map(ProductMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
    
    // No toEntity method since wishlist is just a collection of products
} 