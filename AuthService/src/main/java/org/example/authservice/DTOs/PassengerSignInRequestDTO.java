package org.example.authservice.DTOs;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PassengerSignInRequestDTO {

    private String email ; // username actually referred by email of user / passenger

    private String password ;

}
