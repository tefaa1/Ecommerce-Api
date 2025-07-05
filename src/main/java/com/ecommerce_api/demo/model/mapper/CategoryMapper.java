package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.CategoryRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CategoryResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimCategoryDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimUserDTO;
import com.ecommerce_api.demo.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper{

    private final ProductMapper productMapper;

    @Autowired
    public CategoryMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    public CategoryResponseDTO toDto(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .products(category.getProducts().stream()
                        .map(productMapper::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public SlimCategoryDTO toSlimDto(Category category){
        return SlimCategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .build();
    }

    public Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .description(categoryRequestDTO.getDescription())
                .imageUrl(categoryRequestDTO.getImageUrl())
                .build();
    }
} 