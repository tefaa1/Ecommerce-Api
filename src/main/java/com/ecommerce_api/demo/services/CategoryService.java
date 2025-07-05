package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.CategoryRequestDTO;
import com.ecommerce_api.demo.model.dto.request.ProductRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CategoryResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimCategoryDTO;
import com.ecommerce_api.demo.model.entity.Category;
import java.util.List;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryRequestDTO categoryRequestDTO);
    void updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO);
    void addProductToCategory(Long productId, Long categoryId);
    void deleteProductFromCategory(Long productId, Long categoryId);
    CategoryResponseDTO getCategoryByIdWithProducts(Long id);
    List<SlimCategoryDTO> getAllCategories();
    void deleteCategoryById(Long id);
} 