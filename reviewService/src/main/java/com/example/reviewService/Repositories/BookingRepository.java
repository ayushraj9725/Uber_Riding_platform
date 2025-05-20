package com.example.reviewService.Repositories;

import com.example.reviewService.Models.Booking;
import com.example.reviewService.Models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {

    //  List<Booking> findAllDriverId(Long driverId);

    List<Booking> findAllByDriverIn(List<Driver> drivers);


}
