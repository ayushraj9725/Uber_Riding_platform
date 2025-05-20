package com.example.reviewService.Services ;

import com.example.reviewService.Models.Booking;
import com.example.reviewService.Models.Driver;
import com.example.reviewService.Repositories.BookingRepository;
import com.example.reviewService.Repositories.DriverRepository;
import com.example.reviewService.Repositories.ReviewRepository ;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner ;
import org.springframework.stereotype.Service ;

import java.util.Arrays;
import java.util.List;

// we are writing the class for executing the complex like queries, for just our operational purpose

@Service  // this helps us to manage all over the thing like using repository layer we can review and actually fill entity to the table ,
          // these all things are handled by spring boot because, using this annotation, we are allowing them to do task for us
          // what reviewService will do: it will take the actual data and apply logic, at last operation will execute by this using repository layer , when it needed this class composite that

public class ReviewService1 implements CommandLineRunner {

    public final ReviewRepository reviewRepository ; // this composition injects that ReviewRepository to create obj in service layer and interact with database

    public final BookingRepository bookingRepository ;
    public final DriverRepository driverRepository;

    public ReviewService1(ReviewRepository reviewRepository, BookingRepository bookingRepository,
                          DriverRepository driverRepository) {  // we have to
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.driverRepository = driverRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

//        System.out.println("********************************");
//        Review review = Review
//                .builder()
//                .comment("Amazing riding quality ")
//// .createdAt(new Date())
//// .updatedAt(new Date())  // we can avoid (we do not need to handle it by myself) this, using entity listener because jpa manage auditing related things
//                .rating(4.0)
//                .build(); // code to create a plane java object, for achieving that we have to save this
//
//        reviewRepository.save(review); // this code executes SQL Query
//
//        // although, we can extract / fetch / delete data also from here using this reviewRepository because it allows me methods using them we can perform operation
//
//        List<Review> reviews = reviewRepository.findAll();
//
//        for(Review r : reviews){
//            System.out.println(r.getComment());
//        }

        // we can delete here
        // reviewRepository.deleteById(2L); // that 2nd inserted data at 2 id after running project here we are deleting ,after commenting above review object
/*
        List<Long> driverIds = Arrays.asList(1L,2L,3L,4L,5L);
        List<Driver> drivers = driverRepository.findAllByIdIn(driverIds);

        //  List<Booking> bookings = bookingRepository.findAllByDriverIn(drivers);  one way to execute this buildin

        // 2nd way this

        for(Driver driver : drivers){
            List<Booking> bookings = driver.getBookings();
            bookings.forEach(booking -> System.out.println(booking.getId()));
        }

*/

    }
}
