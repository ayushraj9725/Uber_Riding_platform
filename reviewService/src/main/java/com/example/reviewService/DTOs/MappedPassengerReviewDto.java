package com.example.reviewService.DTOs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappedPassengerReviewDto {

    private Long passengerId ;
    private String passengerComment ;
    private Double passengerRating;
    private Long bookingId ;
    private Date createdAt ;
    private Date updatedAt ;

}
