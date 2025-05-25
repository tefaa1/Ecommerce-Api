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
public class ReviewRequestDTO {

    @NotNull
    private Integer rating;

    private String comment;

    @NotNull
    private Long productId;

    @NotNull
    private Long userId;

}
