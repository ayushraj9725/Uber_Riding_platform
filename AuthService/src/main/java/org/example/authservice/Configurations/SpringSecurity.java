package org.example.authservice.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurity {

    // to use this configuration of spring security, we have to ensure that care about the set it for avoiding the 401 unauthorized http (status).
    // we have to completely set up spring security, using securitychainfilter
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("api/v1/auth/signup/*").permitAll()
                                .requestMatchers("api/v1/auth/signin/*").permitAll()
                )
                .build();
    }

    @Bean // we are allowing spring boot to create object or bean for provided class here, and using configuration spring boot will understand that, what do actually they have to do
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
