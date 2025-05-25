package com.ecommerce_api.demo.services;

import com.ecommerce_api.demo.model.entity.Review;
import com.ecommerce_api.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    @Override
    public Review saveReview(Review review) {
        return null;
    }

    @Override
    public Review updateOrder(Long id, Review review) {
        return null;
    }

    @Override
    public Review getReviewById(Long id) {
        return null;
    }

    @Override
    public List<Review> getAllReviews() {
        return null;
    }

    @Override
    public void deleteReviewById(Long id) {

    }

    @Override
    public void deleteAllReviews() {

    }
}
