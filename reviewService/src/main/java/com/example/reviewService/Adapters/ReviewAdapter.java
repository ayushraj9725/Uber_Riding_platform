package com.example.reviewService.Adapters;

import com.example.reviewService.DTOs.ReviewDto;
import com.example.reviewService.Models.Review;
import org.springframework.stereotype.Component;

@Component
public interface ReviewAdapter {

    public Review ConvertDTO(ReviewDto reviewDto);

}
