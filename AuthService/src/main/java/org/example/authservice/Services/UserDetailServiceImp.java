package org.example.authservice.Services;

import org.example.authservice.Models.Passenger;
import org.example.authservice.Repositories.PassengerRepository;
import org.example.authservice.SecurityHelpers.AuthPassengerDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;


// this class is responsible for loading the user in the form of userDetails object for auth

@Service
@Component
public class UserDetailServiceImp implements UserDetailsService {

    private final PassengerRepository passengerRepository ;

    public UserDetailServiceImp(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);  // email is unique identifier

        if(passenger.isPresent()){
            return new AuthPassengerDetails(passenger.get());
        }else{
            throw new UsernameNotFoundException("User not found , please Register");
        }

    }


}
