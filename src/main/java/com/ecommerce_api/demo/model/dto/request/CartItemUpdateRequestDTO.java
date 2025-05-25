package com.ecommerce_api.demo.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemUpdateRequestDTO {

    // It's called 'update' because any request to the cart will be an update.

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

}
