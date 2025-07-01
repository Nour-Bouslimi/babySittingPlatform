package org.example.babysitting.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.babysitting.entities.User;
import org.example.babysitting.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.crypto.Cipher.SECRET_KEY;
// This class is responsible for generating JWT tokens.
// It can be implemented using libraries like jjwt or java-jwt.
// For now, we will leave it empty as a placeholder.

// You can implement methods to generate tokens, validate tokens, etc. here.

@Component
public class JWTGenerator {
    @Autowired
    UserRepo userRepo;
    private static final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS512);
    // This method generates a JWT token based on the authentication details.
    public String generateToken(Authentication authentication){
        String email = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user = userRepo.findByEmail(email);
        String token = Jwts.builder()
                .setSubject(email) //n7ot l'identif unique elly ch na3mel byh login
                .claim("user", Map.of("id", user.getIdUser(), "email", user.getEmail()))
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        System.out.println("Generated JWT Token: " + token);
        return token;
    }

    //methode ch njareb byha authenification with cin
    //private final String SECRET_KEY = "secret";


    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("roles", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h validité
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();
    }
// This method extracts the email from the JWT token.
    public String getEmailFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); //bch yraja3 l'email
    }

    // This method validates the JWT token.
    public boolean validateToken(String token){

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true; // If parsing is successful, the token is valid
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", e.fillInStackTrace());
            //return false; // If parsing fails, the token is invalid
        }
    }











}
