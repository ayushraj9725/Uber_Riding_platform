package com.example.reviewService.Controllers;


import com.example.reviewService.Adapters.PassengerReviewAdapter;
import com.example.reviewService.DTOs.MappedPassengerReviewDto;
import com.example.reviewService.DTOs.PassengerReviewDto;
import com.example.reviewService.Models.PassengerReview;
import com.example.reviewService.Services.PassengerReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/passenger-reviews")
public class PassengerReviewController {

    private final PassengerReviewService passengerReviewService;
    private  final PassengerReviewAdapter passengerReviewAdapter;

    public PassengerReviewController(PassengerReviewService passengerReviewService, PassengerReviewAdapter passengerReviewAdapter) {
        this.passengerReviewService = passengerReviewService;
        this.passengerReviewAdapter = passengerReviewAdapter;
    }

    @PostMapping
    public ResponseEntity<?> publishPassengerReview(@Validated  @RequestBody PassengerReviewDto request){

        try{
            PassengerReview inComing = this.passengerReviewAdapter.ConvertDTOPRtoB(request);

            if(inComing == null){
                return new ResponseEntity<>("unable to find data ", HttpStatus.BAD_REQUEST);
            }

            PassengerReview passengerReview = this.passengerReviewService.publishPassengerReview(inComing);

            MappedPassengerReviewDto response = MappedPassengerReviewDto.builder()
                    .passengerId(passengerReview.getId())
                    .passengerRating(passengerReview.getPassengerRating())
                    .passengerComment(passengerReview.getPassengerComment())
                    .bookingId(passengerReview.getBooking().getId())
                    .createdAt(passengerReview.getCreatedAt())
                    .updatedAt(passengerReview.getUpdatedAt())
                    .build();


            return new ResponseEntity<>(passengerReview, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping
    public ResponseEntity<List<PassengerReview>> getAllPassengerReviews(){
        List<PassengerReview> passengerReviews = this.passengerReviewService.findAllPassengerReview();
        return new ResponseEntity<>(passengerReviews,HttpStatus.OK);
    }


    @GetMapping("/{passengerReviewId}")
    public ResponseEntity<?> findPassengerReviewById(@PathVariable  Long passengerReviewId){
        try{
            Optional<PassengerReview> passengerReview =this.passengerReviewService.findPassengerReviewById(passengerReviewId);
            return new ResponseEntity<>(passengerReview,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{passengerReviewId}")
    public ResponseEntity<?> deletePassengerReviewById(@PathVariable Long passengerReviewId){
        try{
            boolean isDeleted = this.passengerReviewService.deletePassengerReviewById(passengerReviewId);
            if(isDeleted){
                return new ResponseEntity<>("unable to delete review on " + passengerReviewId , HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Delete Review Successfully ", HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{passengerReviewId}")
    public ResponseEntity<?> updatePassengerReview(@PathVariable Long passengerReviewId , @RequestBody  PassengerReview newRequest){
        try{
            PassengerReview passengerReview = this.passengerReviewService.updatePassengerReview(passengerReviewId,newRequest);
            return new ResponseEntity<>(passengerReview,HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
