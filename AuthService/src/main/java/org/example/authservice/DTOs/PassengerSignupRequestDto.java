package org.example.authservice.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {

    private String name ;

    private String email ;

    private String password ;

    private String phoneNumber ;


}
