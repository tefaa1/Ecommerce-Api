package com.ecommerce_api.demo.model.mapper;

import com.ecommerce_api.demo.model.dto.request.ReviewRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.model.entity.Review;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    private final ProductMapper productMapper;

    private final UserMapper userMapper;

    @Autowired
    public ReviewMapper(ProductMapper productMapper,
                        UserMapper userMapper){

        this.productMapper = productMapper;
        this.userMapper = userMapper;
    }

    public ReviewResponseDTO toDto(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .slimProductDTO(productMapper.toSlimDto(review.getProduct()))
                .slimUserDTO(userMapper.toSlimDto(review.getUser()))
                .build();
    }

    public Review toEntity(ReviewRequestDTO reviewRequestDTO,User user,Product product) {
        return Review.builder()
                .rating(reviewRequestDTO.getRating())
                .comment(reviewRequestDTO.getComment())
                .user(user)
                .product(product)
                .build();
    }
} 