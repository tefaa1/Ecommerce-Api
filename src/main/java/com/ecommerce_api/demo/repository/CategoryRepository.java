package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products WHERE c.id = :categoryId")
    Optional<Category>findCategoryWithProductsByCategoryId(@Param("categoryId")Long categoryId);

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products")
    Optional<List<Category>>findAllCategoriesWithProductsById();
} 