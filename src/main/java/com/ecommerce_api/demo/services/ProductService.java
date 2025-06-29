package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.ProductRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ProductResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDTO saveProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO);
    ProductResponseDTO getProductById(Long id);
//    stay here until I need them
//    Product getProductWithCartItems(Long id);
//    Product getProductWithWishlist(Long id);
//    Product getProductWithOrderItems(Long id);
//    Product getProductWithReviews(Long id);
//    Product getProductWithAllRelations(Long id);
    List<ProductResponseDTO>getAllProducts();
    void deleteProductById(Long id);
}
