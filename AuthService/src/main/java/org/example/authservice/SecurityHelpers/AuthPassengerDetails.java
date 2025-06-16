package org.example.authservice.SecurityHelpers;

import org.example.authservice.Models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// why do we need this class?
// because spring security works on userDetails polymorphic type for authentication

public class AuthPassengerDetails extends Passenger implements UserDetails
{
    private String username ; // we are keeping it bcz spring security treats unique username for a process, so we can use email,id,name,phoneNumber for username

    private String password ;

    public AuthPassengerDetails(Passenger passenger){
        this.username = passenger.getEmail(); //  we are keeping the email as username
        this.password = passenger.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() { // returning the user email
        return this.username;
    }

    @Override
    public String getPassword(){  // return the passenger password for matching to the Encrypted
        return this.password;
    }
    // these are the below set of method are not much of a concern

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
