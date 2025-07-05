package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.CategoryRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CategoryResponseDTO;
import com.ecommerce_api.demo.model.dto.slimDto.SlimCategoryDTO;
import com.ecommerce_api.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){

        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getCategory(@PathVariable Long id){

        CategoryResponseDTO categoryResponseDTO= categoryService.getCategoryByIdWithProducts(id);
        return ResponseEntity.ok().body(categoryResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?>getAllCategories(){

        List<SlimCategoryDTO>slimCategoryDTOS = categoryService.getAllCategories();
        return ResponseEntity.ok().body(slimCategoryDTOS);
    }

    @PostMapping
    public ResponseEntity<?>createCategory(@RequestBody @Valid CategoryRequestDTO categoryRequestDTO){

        CategoryResponseDTO categoryResponseDTO = categoryService.saveCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?>updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO){

        categoryService.updateCategory(id,categoryRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<?> addProductToCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {

        categoryService.addProductToCategory(productId,categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<?> deleteProductFromCategory(
            @PathVariable Long categoryId,
            @PathVariable Long productId) {

        categoryService.deleteProductFromCategory(productId,categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteCategory(@PathVariable Long id){

        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }
}
