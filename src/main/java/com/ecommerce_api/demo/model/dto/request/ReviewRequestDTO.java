package com.ecommerce_api.demo.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDTO {

    private Integer rating;

    private String comment;

    private Long productId;

    private Long userId;

}
