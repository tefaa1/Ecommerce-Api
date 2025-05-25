package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.ReviewRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.model.entity.Review;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public static ReviewResponseDTO toDto(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .productId(review.getProduct().getId())
                .userId(review.getUser().getId())
                .build();
    }

    public static Review toEntity(ReviewRequestDTO reviewRequestDTO, Product product, User user) {
        return Review.builder()
                .rating(reviewRequestDTO.getRating())
                .comment(reviewRequestDTO.getComment())
                .product(product)
                .user(user)
                .build();
    }
} 