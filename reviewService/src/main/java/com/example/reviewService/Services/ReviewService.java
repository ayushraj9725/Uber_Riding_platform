package com.example.reviewService.Services;

import com.example.reviewService.Models.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ReviewService {

    public Optional<Review> findReviewById(Long Id);

    public List<Review> findAllReviews();

    public boolean deleteReviewById(Long Id);

    public Review updateReview(Long id , Review review);

    public Review publishReview(Review review);

}
