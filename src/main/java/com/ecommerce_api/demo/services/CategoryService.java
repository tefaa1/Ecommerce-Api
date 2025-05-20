package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Category;
import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category getCategoryById(Long id);
    Category getCategoryWithProducts(Long id);
    List<Category> getAllCategories();
    void deleteCategoryById(Long id);
    void deleteAllCategories();
} 