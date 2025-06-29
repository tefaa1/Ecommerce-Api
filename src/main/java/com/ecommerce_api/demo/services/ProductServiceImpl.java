package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.ProductRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.mapper.ProductMapper;
import com.ecommerce_api.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,ProductMapper productMapper){

        this.productRepository = productRepository;
        this.productMapper = productMapper;

    }
    @Override
    public ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO) {

        Product product = productMapper.toEntity(productRequestDTO);
        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not found"));

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStockQuantity());
        product.setDiscount(product.getDiscount());
        product.setImageUrl(product.getImageUrl());

        return productMapper.toDto(product);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + id + " not found"));

       return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        List<Product>products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID" + id + " not found"));

        productRepository.delete(product);
    }
}
