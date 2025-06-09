package org.example.authservice.Services;


import org.example.authservice.DTOs.PassengerDTO;
import org.example.authservice.DTOs.PassengerSignupRequestDto;
import org.example.authservice.Models.Passenger;
import org.example.authservice.Repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDTO signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){

        Passenger passenger = Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))  // TODO : Encrypt the Password => password has encrypted by bCryptPasswordEncoder spring boot using java bean of spring security
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDTO.fromPassenger(newPassenger);

    }

}
