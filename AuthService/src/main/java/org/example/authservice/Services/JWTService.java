package org.example.authservice.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.expiry}")
    private int expirationTime ;

    @Value("${jwt.secret}")
    private String secret ;

    /*
     this method creates a brand-new JWT token for us based on payload
     @return
     */

    private String createToken(Map<String,Object> payload , String email){

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime * 1000L);

        // we are setting up the secret key over here for passing in the signature

        // SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // if we want, we can use it there in place of signWith param only key

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                //.signWith(SignatureAlgorithm.HS256,secret) // it is deprecated so we should use this
                .signWith(getKey())
                .compact();

    }

    private Claims extractAllPayload(String token){

        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllPayload(token);
        return claimResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    // this method checks if the token expiry was before the current time stamp
    // @param token
    // return true if is expired else false

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // another way to check the expiry time using the email extraction

    private String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // now we are going to validate the extracted email is expired or not

    private Boolean validateToken(String email, String token){
        final String emailFetchedFromToken = extractEmail(token);
        return (emailFetchedFromToken.equals(email)) && isTokenExpired(token);
    }

    private Key getKey(){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return key ;
    }

}
