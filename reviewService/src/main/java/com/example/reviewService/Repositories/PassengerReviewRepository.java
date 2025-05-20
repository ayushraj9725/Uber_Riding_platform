package com.example.reviewService.Repositories;

import com.example.reviewService.Models.PassengerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PassengerReviewRepository extends JpaRepository<PassengerReview,Long> {

    // it will help me to do all the queried for us, related to passenger review!

}
