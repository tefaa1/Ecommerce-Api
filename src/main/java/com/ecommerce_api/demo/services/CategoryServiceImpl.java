package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.CategoryRequestDTO;
import com.ecommerce_api.demo.model.dto.response.CategoryResponseDTO;
import com.ecommerce_api.demo.model.entity.Category;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.mapper.CategoryMapper;
import com.ecommerce_api.demo.repository.CategoryRepository;
import com.ecommerce_api.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper,
                               ProductRepository productRepository)
    {

        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.productRepository = productRepository;
    }
    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryRequestDTO) {

        Category category = categoryMapper.toEntity(categoryRequestDTO);
        return categoryMapper.toDto(categoryRepository.save(category));

    }

    @Override
    public void updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        category.setImageUrl(categoryRequestDTO.getImageUrl());

        categoryRepository.save(category);
    }


    @Override
    public void addProductToCategory(Long productId, Long categoryId){

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        if(product.getCategory() != null){
            throw new IllegalArgumentException("Product is already assigned to a another category");
        }

        category.addProduct(product);
        categoryRepository.save(category);
    }

    @Override
    public void deleteProductFromCategory(Long productId, Long categoryId) {

        Category category = categoryRepository.findCategoryWithProductsByCategoryId(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found"));

        if(!category.getProducts().contains(product)){
            throw new IllegalArgumentException("Product is not exist in this category");
        }

        category.removeProduct(product);
        categoryRepository.save(category);
    }

    @Override
    public CategoryResponseDTO getCategoryByIdWithProducts(Long id) {

        Category category = categoryRepository.findCategoryWithProductsByCategoryId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found"));

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        List<Category>categories = categoryRepository.findAllCategoriesWithProductsById()
                .orElseThrow(() -> new ResourceNotFoundException("There is no categories"));

        return categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategoryById(Long id) {

        Category category = categoryRepository.findCategoryWithProductsByCategoryId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + id + " not found"));

        for (Product product : category.getProducts()) {
            category.removeProduct(product);
        }
        categoryRepository.delete(category);
    }
}
