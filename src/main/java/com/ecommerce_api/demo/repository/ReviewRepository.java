package com.ecommerce_api.demo.repository;

import com.ecommerce_api.demo.model.dto.response.ReviewResponseDTO;
import com.ecommerce_api.demo.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Boolean existsByUserEmailAndProductId(String email,Long id);
    Optional<List<Review>>findAllByUserEmail(String email);
    Optional<List<Review>>findAllByProductId(Long id);
}