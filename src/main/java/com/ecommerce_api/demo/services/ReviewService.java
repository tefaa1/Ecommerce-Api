package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.Review;
import java.util.List;

public interface ReviewService {
    Review saveReview(Review review);
    Review updateOrder(Long id, Review review);
    Review getReviewById(Long id);
    List<Review> getAllReviews();
    void deleteReviewById(Long id);
    void deleteAllReviews();
} 