package com.ecommerce_api.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDTO {

    private Long id;

    private ProductResponseDTO productResponseDTO;

    private Integer quantity;

    private BigDecimal priceAtOrderTime;

}
