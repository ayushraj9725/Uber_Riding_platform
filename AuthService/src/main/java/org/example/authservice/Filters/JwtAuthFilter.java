package org.example.authservice.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.Services.JWTService;
import org.example.authservice.Services.UserDetailServiceImp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {


    // @Autowired
    private final UserDetailServiceImp userDetailService ;

    private final JWTService jwtService ;

    public JwtAuthFilter(UserDetailServiceImp userDetailService, JWTService jwtService) {
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // we are writing the function to process the cookie coming from the client request, and try to store that

        String token = null ;

        if(request.getCookies() != null){
            for(Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("JwtToken")){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null){
            // user has not provided and JWT Token hence request should not go forward
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ;
        }

        // if any token found then we have to validate it, so we are calling the extractEmail

        String email = jwtService.extractEmail(token);

        if(email != null){
            // we need the userDetail so we are calling the loadUserByUsername method that is present in the UserDetailServiceImp class comes from UserDetailService
            UserDetails userDetails = userDetailService.loadUserByUsername(email);  // spring Security, allow here holding the details of user in the form object variable userDetails

            // now we can check the token by calling the method validateToken that comes from JwtService class
            if(jwtService.validateToken(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null);

                // usernamePasswordAuthenticationToken.setDetails(userDetails); // we are setting up the basic detail of user here, but I can also make it more secure
                // by using the spring internal converter compact class, that take input from the servlet class and make it more compatible in the way of spring

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // this makes sure the object usernamePassAuthToken is remembered to spring

            }
        }

        filterChain.doFilter(request, response);

    }
}
