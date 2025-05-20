package com.example.reviewService.Controllers;

import com.example.reviewService.Adapters.ReviewAdapter;
import com.example.reviewService.DTOs.MappedReviewDto;
import com.example.reviewService.DTOs.ReviewDto;
import com.example.reviewService.Models.Review;
import com.example.reviewService.Services.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {


    private final ReviewService reviewService;
    private final ReviewAdapter reviewAdapter;

    public ReviewController(ReviewService reviewService,ReviewAdapter reviewAdapter ){
        this.reviewService = reviewService ;
        this.reviewAdapter = reviewAdapter;
    }

    @PostMapping // use postMapping for creating a new review using api
    public ResponseEntity<?> publishReview(@Validated @RequestBody ReviewDto request){
        try{
            Review inComing = this.reviewAdapter.ConvertDTO(request);

            if(inComing == null){
                return new ResponseEntity<>("Unable to get review data ",HttpStatus.BAD_REQUEST);
            }

            Review review = this.reviewService.publishReview(inComing);

            MappedReviewDto response = MappedReviewDto.builder()
                    .id(review.getId())
                    .comment(review.getComment())
                    .rating(review.getRating())
                    .booking(review.getBooking().getId())
                    .createdAt(review.getCreatedAt())
                    .updatedAt(review.getUpdatedAt())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping  // only using getMapping for finding all the reviews at a time
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> reviews = this.reviewService.findAllReviews();
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }

    // getMapping  using the path variable reviewId /v1 /v2 ets .

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> findReviewById(@PathVariable Long reviewId){
        try{
            Optional<Review> review = this.reviewService.findReviewById(reviewId);
            return new ResponseEntity<>(review,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    // now we are going to delete the review using deleteMapping over her below
    @DeleteMapping("/{reviewId}")
   // @Transactional : making this here comment because it wants to delete data review from the booking review table which will affect the not null booking_id property and through internal server error
    public ResponseEntity<?> deleteReviewById(@Validated @PathVariable Long reviewId){

        try{
            boolean isDeleted = this.reviewService.deleteReviewById(reviewId);
            if(isDeleted){
                return new ResponseEntity<>("unable to delete review on " + reviewId , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Delete Review Succesfully ",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // now we are going to update the review using putMapping over here below, by specifying some data, not all

    @PutMapping("/{reviewId}")
    @Transactional
    public ResponseEntity<?> updateReview(@Validated @PathVariable Long reviewId ,@RequestBody Review request){
        try{
            Review review = this.reviewService.updateReview(reviewId,request);
            return new ResponseEntity<>(review,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}
