package org.example.authservice.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expirationTime ;

    @Value("${jwt.secret}")
    private String secret ;

    /*
     this method creates a brand-new JWT token for us based on payload
     @return
     */

    public String createToken(Map<String,Object> payload , String email){

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

    public String createToken(String email){
        return createToken(new HashMap<>(),email);
    }

    public Claims extractAllPayload(String token){

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

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    // this method checks if the token expiry was before the current time stamp
    // @param token
    // return true if is expired else false

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // another way to check the expiry time using the email extraction

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // now we are going to validate the extracted email is expired or not

    public Boolean validateToken(String email, String token){
        final String emailFetchedFromToken = extractEmail(token);
        return (emailFetchedFromToken.equals(email)) && isTokenExpired(token);
    }

    private Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    // we want to extract any payload using this

    public String extractPayload(String token,String payloadKey){
        Claims claims = extractAllPayload(token);
        return claims.get(payloadKey).toString();   // we can access it using Object in place of string
    }


    @Override
    public void run(String... args) throws Exception {

        Map<String,Object> payload = new HashMap<>();

        payload.put("email","abc@123");
        payload.put("PhoneNumber","9384928939");

        String result = createToken(payload,"ayush@123");

        System.out.println("generated token : "+result);
        System.out.println("extracted payload is : "+extractPayload(result,"email") );

    }
}
