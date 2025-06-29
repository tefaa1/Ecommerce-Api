package com.ecommerce_api.demo.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDTO {

    private Long id;

    private ProductResponseDTO productResponseDTO;

    private Integer quantity;

}
