package com.example.reviewService.Services;


import com.example.reviewService.Models.PassengerReview;
import com.example.reviewService.Repositories.PassengerReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.FetchNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerReviewServiceImp implements PassengerReviewService{


    // this class is made for communicating to the database what made by passenger review module in our db, this service helps us to find the passengerReviewByID and execute queries

    public final PassengerReviewRepository passengerReviewRepository ;

    public PassengerReviewServiceImp(PassengerReviewRepository passengerReviewRepository) {
        this.passengerReviewRepository = passengerReviewRepository;
    }

    @Override
    public Optional<PassengerReview> findPassengerReviewById(Long passengerReviewId) throws EntityNotFoundException{
        try{
            Optional<PassengerReview> passengerReview = this.passengerReviewRepository.findById(passengerReviewId);

            if(passengerReview.isEmpty()){
                throw new EntityNotFoundException("PassengerReview with id" + passengerReviewId + " not found");
            }

            return passengerReview ;

        }catch(Exception e){
            e.printStackTrace();
            if(e.getClass() == EntityNotFoundException.class){
                throw new FetchNotFoundException("PassengerReview with id " + passengerReviewId + " not found", passengerReviewId);
            }
            throw new FetchNotFoundException("Unable to fetch, PLease try again later!", passengerReviewId);
        }

    }

    @Override
    public List<PassengerReview> findAllPassengerReview() {
        return this.passengerReviewRepository.findAll(); // this will execute the query of find all the passengerReview and return
    }

    @Override
    @Transactional
    public PassengerReview publishPassengerReview(PassengerReview passengerReview) {
        return this.passengerReviewRepository.save(passengerReview); // when we get passengerReview data, we save like this and jpa will execute queries for us to store review done by a driver in the passengerReview table
    }

    @Override
    public PassengerReview updatePassengerReview(Long passengerReviewId, PassengerReview NewPassengerReview) {

        PassengerReview passengerReview= this.passengerReviewRepository.findById(passengerReviewId).orElseThrow(EntityNotFoundException::new);
        if(NewPassengerReview.getPassengerRating() != 0){
            passengerReview.setPassengerRating(NewPassengerReview.getPassengerRating());
        }
        if(NewPassengerReview.getPassengerComment() != null){
            passengerReview.setPassengerComment(NewPassengerReview.getPassengerComment());
        }
        return this.passengerReviewRepository.save(passengerReview);

    }

    @Override
    public boolean deletePassengerReviewById(Long Id) {
        try {
            PassengerReview passengerReview = this.passengerReviewRepository.findById(Id).orElseThrow(EntityNotFoundException::new);
            this.passengerReviewRepository.delete(passengerReview);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
