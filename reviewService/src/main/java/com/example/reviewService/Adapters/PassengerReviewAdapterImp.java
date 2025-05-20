package com.example.reviewService.Adapters;

import com.example.reviewService.DTOs.PassengerReviewDto;
import com.example.reviewService.Models.Booking;
import com.example.reviewService.Models.PassengerReview;
import com.example.reviewService.Repositories.BookingRepository;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PassengerReviewAdapterImp implements PassengerReviewAdapter{

    private final BookingRepository bookingRepository ;

    public PassengerReviewAdapterImp(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;

    }

    @Override
    public PassengerReview ConvertDTOPRtoB(PassengerReviewDto passengerReviewDto) {

        try {
            Optional<Booking> booking = this.bookingRepository.findById(passengerReviewDto.getBookingId());

            return booking.map(value -> PassengerReview.builder()
                    .passengerRating(passengerReviewDto.getPassengerRating())
                    .passengerComment(passengerReviewDto.getPassengerComment())
                    .booking(value)
                    .build()).orElse(null);

        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
