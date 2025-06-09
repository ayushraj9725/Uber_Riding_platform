package org.example.authservice.Controller;

import org.example.authservice.DTOs.PassengerDTO;
import org.example.authservice.DTOs.PassengerSignupRequestDto;
import org.example.authservice.Services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    public AuthService authService ;

    public AuthController(AuthService authService){
        this.authService = authService ;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDTO> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){

        PassengerDTO response = authService.signUpPassenger(passengerSignupRequestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED) ;

    }

}
