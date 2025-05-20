package com.example.reviewService.Adapters;

import com.example.reviewService.DTOs.PassengerReviewDto;
import com.example.reviewService.Models.PassengerReview;
import org.springframework.stereotype.Component;

@Component // allow to spring handle the creating object or handling BEANs
public interface PassengerReviewAdapter {

    public PassengerReview ConvertDTOPRtoB(PassengerReviewDto passengerReviewDto);

}
