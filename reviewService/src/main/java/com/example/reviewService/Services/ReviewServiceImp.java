package com.example.reviewService.Services;

import com.example.reviewService.Models.Review;
import com.example.reviewService.Repositories.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImp implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImp(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> findReviewById(Long Id) throws EntityNotFoundException {
        Optional<Review> review;

        try {
            review = this.reviewRepository.findById(Id);
            if (review.isEmpty()) {
                throw new EntityNotFoundException("Review with id " + Id + " not found");
            }
        }catch (Exception e){
            e.printStackTrace();
            if(e.getClass() == EntityNotFoundException.class){
                throw new FetchNotFoundException("Review with id " + Id + " not found", Id);
            }
            throw new FetchNotFoundException("Unable to fetch, PLease try again later!", Id);
        }

        return review;
    }

    @Override
    public List<Review> findAllReviews() {

        return this.reviewRepository.findAll();
        // return List.of();
    }

    @Override
    public boolean deleteReviewById(Long Id) {

        try {
            Review review = this.reviewRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
            this.reviewRepository.delete(review);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override

    public Review updateReview(Long id, Review newReviewData) {

        Review review = this.reviewRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if(newReviewData.getRating() != null){
            review.setRating(newReviewData.getRating());
        }
        if(newReviewData.getComment() != null){
            review.setComment(newReviewData.getComment());
        }
        return this.reviewRepository.save(review);

    }

    @Override
    @Transactional
    public Review publishReview(Review review) {
        return this.reviewRepository.save(review);  // when we get review data, we save like this and return from here to our database for storing review correspond to booking
    }




}
