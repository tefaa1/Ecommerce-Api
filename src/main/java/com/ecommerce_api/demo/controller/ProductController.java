package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.ProductRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO){

        ProductResponseDTO productResponseDTO = productService.saveProduct(productRequestDTO);
        return new ResponseEntity<>(productResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO productRequestDTO){

        ProductResponseDTO productResponseDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id){

        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(){

        List<ProductResponseDTO> productResponseDTOList = productService.getAllProducts();
        return ResponseEntity.ok(productResponseDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteProductById(@PathVariable Long id){

        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
