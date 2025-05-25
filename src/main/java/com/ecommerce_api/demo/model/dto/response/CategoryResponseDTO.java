package com.ecommerce_api.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Set<ProductResponseDTO> products;
}
