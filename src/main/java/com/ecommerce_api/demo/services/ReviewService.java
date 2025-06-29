package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.dto.request.ReviewRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.model.entity.Review;
import java.util.List;

public interface ReviewService {
    void saveReview(ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO getReviewById(Long id);
    List<ReviewResponseDTO>getAllReviewsForUser();
    List<ReviewResponseDTO>getAllReviewsForProduct(Long id);
    List<ReviewResponseDTO>getAllReviews();
    void deleteReviewById(Long id);
} 