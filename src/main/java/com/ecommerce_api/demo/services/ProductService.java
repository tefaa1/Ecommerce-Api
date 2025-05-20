package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Order;
import com.ecommerce_api.demo.entity.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product getProductById(Long id);
    Product getProductWithCartItems(Long id);
    Product getProductWithWishlist(Long id);
    Product getProductWithOrderItems(Long id);
    Product getProductWithReviews(Long id);
    Product getProductWithAllRelations(Long id);
    List<Product>getAllProducts();
    void deleteProductById(Long id);
    void deleteAllProducts();
}
