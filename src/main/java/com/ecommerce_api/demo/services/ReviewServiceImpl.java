package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.exception.ResourceNotFoundException;
import com.ecommerce_api.demo.model.dto.request.ReviewRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.model.entity.Product;
import com.ecommerce_api.demo.model.entity.Review;
import com.ecommerce_api.demo.model.entity.User;
import com.ecommerce_api.demo.model.mapper.ReviewMapper;
import com.ecommerce_api.demo.repository.ProductRepository;
import com.ecommerce_api.demo.repository.ReviewRepository;
import com.ecommerce_api.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewMapper reviewMapper,
                             UserService userService,
                             ProductService productService) {

        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void saveReview(ReviewRequestDTO reviewRequestDTO) {

        String email = userService.getTheCurrentUserEmail();

        if(reviewRepository.existsByUserEmailAndProductId(email,reviewRequestDTO.getProductId())){
           throw new RuntimeException("you already have a review for this product");
        }
        else{
            User user = userService.getUserByEmail(email);

            Long productId = reviewRequestDTO.getProductId();
            Product product = productService.getProductEntityById(productId);

            Review review = reviewMapper.toEntity(reviewRequestDTO,user,product);
            reviewRepository.save(review);
        }

    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {

        // any one can see any review
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + id + " not found"));

        return reviewMapper.toDto(review);
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsForUser() {

        String email = userService.getTheCurrentUserEmail();

        List<Review> reviews = reviewRepository.findAllByUserEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("There is no reviews for you"));

        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getAllReviewsForProduct(Long id) {

        List<Review> reviews = reviewRepository.findAllByProductId(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no reviews for this product"));

        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDTO> getAllReviews() {

        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReviewById(Long id) {

        String email = userService.getTheCurrentUserEmail();

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + id + " not found"));

        if(review.getUser().getEmail().equals(email)){
            reviewRepository.delete(review);
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied, This is not your review");
        }
    }
}
