package com.ecommerce_api.demo.model.dto.slimDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlimCategoryDTO {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;
}
