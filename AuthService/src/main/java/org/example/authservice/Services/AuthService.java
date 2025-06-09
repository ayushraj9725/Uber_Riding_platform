package org.example.authservice.Services;


import org.example.authservice.DTOs.PassengerDTO;
import org.example.authservice.DTOs.PassengerSignupRequestDto;
import org.example.authservice.Models.Passenger;
import org.example.authservice.Repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public PassengerDTO signUpPassenger(PassengerSignupRequestDto passengerSignupRequestDto){

        Passenger passenger = Passenger.builder()
                .email(passengerSignupRequestDto.getEmail())
                .name(passengerSignupRequestDto.getName())
                .password(passengerSignupRequestDto.getPassword())  // TODO : Encrypt the Password, please
                .phoneNumber(passengerSignupRequestDto.getPhoneNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDTO.fromPassenger(newPassenger);

    }

}
