package com.example.reviewService.Repositories;


import com.example.reviewService.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


// this class is made for communicating to the database what made by review module in our database, this repository review class help to interact and execute queries for us
// Row or hibernates queries any types it can do, or methods automatically do using interface and annotation

@Repository
public interface ReviewRepository extends JpaRepository<Review , Long> {
      // it is an interface that takes property and methods from jpaRepository interface which has all the property they can actually
      // help me to interact with a database and execute the queries

    Integer countAllByRatingIsLessThanEqual(Double rating);

    List<Review> findAllByRatingIsLessThanEqual(Double rating);

    List<Review> findAllByCreatedAtBefore(Date date);

    @Query("select r from Booking b inner join Review r where b.id = :bookingId")
    Review findReviewByBookingId(Long bookingId);

}
