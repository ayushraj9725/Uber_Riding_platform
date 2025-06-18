package org.example.authservice.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.DTOs.PassengerDTO;
import org.example.authservice.DTOs.PassengerSignInRequestDTO;
import org.example.authservice.DTOs.PassengerSignInResponseDTO;
import org.example.authservice.DTOs.PassengerSignupRequestDto;
import org.example.authservice.Services.AuthService;
import org.example.authservice.Services.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry ;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public AuthService authService ;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService){
        this.authService = authService ;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDTO> signup(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){

        PassengerDTO response = authService.signUpPassenger(passengerSignupRequestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED) ;

    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signin(@RequestBody PassengerSignInRequestDTO passengerSignInRequestDTO, HttpServletResponse response){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                passengerSignInRequestDTO.getEmail(),passengerSignInRequestDTO.getPassword()
        ));

        if(authentication.isAuthenticated()){

            String jwtToken = jwtService.createToken(passengerSignInRequestDTO.getEmail());

            ResponseCookie cookie = ResponseCookie.from("JwtToken",jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return new ResponseEntity<>(PassengerSignInResponseDTO.builder().success(true).build(),HttpStatus.OK);

            // return new ResponseEntity<>("Successfully logged in",HttpStatus.OK);
        }

         else throw new UsernameNotFoundException("User not found");
    }


}
