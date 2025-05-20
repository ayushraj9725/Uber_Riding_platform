package com.example.reviewService.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    private String comment;

    private Double rating;

    private Long bookingId ;


    // dto is used to transfer data from one layer to another layer,
    // so it here is used to transfer data from controller to service layer using Adapters that we wrote in the adapter package
    // reviewAdepter helps us to convert reviewDto to a review object and vice versa so that we can use it in service layer for our business logic and client call

}
