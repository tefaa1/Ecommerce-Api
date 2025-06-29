package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.ProductRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDTO toDto(Product product){
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .discount(product.getDiscount())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    public Product toEntity(ProductRequestDTO productRequestDTO){
        return Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .stockQuantity(productRequestDTO.getStockQuantity())
                .discount(productRequestDTO.getDiscount())
                .imageUrl(productRequestDTO.getImageUrl())
                .build();
    }
}
