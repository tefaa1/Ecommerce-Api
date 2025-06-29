package com.ecommerce_api.demo.controller;

import com.ecommerce_api.demo.model.dto.request.ReviewRequestDTO;
import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){

        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Void>createReview(@RequestBody @Valid ReviewRequestDTO reviewRequestDTO){

        reviewService.saveReview(reviewRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO>getReview(@PathVariable Long id){

        ReviewResponseDTO reviewResponseDTO = reviewService.getReviewById(id);
        return ResponseEntity.ok().body(reviewResponseDTO);
    }

    @GetMapping("/myReviews")
    public ResponseEntity<List<ReviewResponseDTO>>getMyReviews(){

        List<ReviewResponseDTO>reviewResponseDTOS = reviewService.getAllReviewsForUser();
        return ResponseEntity.ok().body(reviewResponseDTOS);
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>>getProductReviews(@PathVariable Long id){

        List<ReviewResponseDTO>reviewResponseDTOS = reviewService.getAllReviewsForProduct(id);
        return ResponseEntity.ok().body(reviewResponseDTOS);
    }

    // For Admins
    @GetMapping("/allReviews")
    public ResponseEntity<List<ReviewResponseDTO>>getAllReviews(){

        List<ReviewResponseDTO>reviewResponseDTOS = reviewService.getAllReviews();
        return ResponseEntity.ok().body(reviewResponseDTOS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deleteReview(@PathVariable Long id){

        reviewService.deleteReviewById(id);
        return ResponseEntity.ok().build();
    }
}
