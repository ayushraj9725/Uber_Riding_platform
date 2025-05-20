package com.example.reviewService.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PassengerReviewDto {

    private String passengerComment;

    private Double passengerRating;

    private Long bookingId ;


}
