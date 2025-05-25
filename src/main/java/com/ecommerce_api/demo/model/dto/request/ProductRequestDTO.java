package com.ecommerce_api.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Integer discount;

    private String imageUrl;

}
