package com.example.reviewService.Adapters;

import com.example.reviewService.DTOs.ReviewDto;
import com.example.reviewService.Models.Booking;
import com.example.reviewService.Models.Review;
import com.example.reviewService.Repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReviewAdapterImp implements ReviewAdapter{

    private final BookingRepository bookingRepository ;

    public ReviewAdapterImp(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;

    }

    @Override
    public Review ConvertDTO(ReviewDto reviewDto) {
        try{
            Optional<Booking> booking = this.bookingRepository.findById(reviewDto.getBookingId());

            return booking.map(value -> Review.builder()
                            .rating(reviewDto.getRating())
                            .comment(reviewDto.getComment())
                            .booking(value)
                            .build()).orElse(null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
