package com.example.reviewService.DTOs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappedReviewDto {

    private Long id ;
    private String comment ;
    private Double rating ;
    private Long booking;
    private Date createdAt;
    private Date updatedAt;

}
