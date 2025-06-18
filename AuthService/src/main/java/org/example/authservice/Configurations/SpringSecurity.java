package org.example.authservice.Configurations;

import org.example.authservice.Repositories.PassengerRepository;
import org.example.authservice.Services.UserDetailServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity

public class SpringSecurity implements WebMvcConfigurer {

    private final PassengerRepository passengerRepository ; // although we can use the Autowired to avoid constructor injection here

    public SpringSecurity(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailServiceImp(passengerRepository);
    }

    // to use this configuration of spring security, we have to ensure that care about the set it for avoiding the 401 unauthorized http (status).
    // we have to completely set up spring security, using securitychainfilter
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors-> cors.disable())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("api/v1/auth/signup/*").permitAll()
                                .requestMatchers("api/v1/auth/signin/*").permitAll()
                                .requestMatchers("api/v1/auth/validate").permitAll()
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){   // encode the password
        return new BCryptPasswordEncoder();
    }

    @Bean // we are allowing spring boot to create object or bean for provided class here, and using configuration spring boot will understand that, what do actually they have to do
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // https://medium.com/@benaya7/cors-configuration-in-spring-security-and-webmvc-lets-get-it-out-of-the-way-47ba059ca524

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

}
