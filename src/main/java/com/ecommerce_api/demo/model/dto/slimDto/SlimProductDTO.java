package com.ecommerce_api.demo.model.dto.slimDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlimProductDTO {

    private Long id;

    private String name;

    private BigDecimal discountedPrice;

    private String imageUrl;
}
