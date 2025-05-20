package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.entity.Product;
import com.ecommerce_api.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    @Override
    public Product saveProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product getProductWithCartItems(Long id) {
        return null;
    }

    @Override
    public Product getProductWithWishlist(Long id) {
        return null;
    }

    @Override
    public Product getProductWithOrderItems(Long id) {
        return null;
    }

    @Override
    public Product getProductWithReviews(Long id) {
        return null;
    }

    @Override
    public Product getProductWithAllRelations(Long id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {

    }

    @Override
    public void deleteAllProducts() {

    }
}
