package org.example.authservice.DTOs;

import lombok.*;
import org.example.authservice.Models.Passenger;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDTO {

    private Long id ;

    private String name ;

    private String email ;

    private String password ; // that encrypted password what has saved while making or registration it will respond back

    private String phoneNumber ;

    private Date createdAt ;


    public static PassengerDTO fromPassenger(Passenger passenger){

        PassengerDTO result = PassengerDTO.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .password(passenger.getPassword())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
                .build();

        return result ;

    }

}
