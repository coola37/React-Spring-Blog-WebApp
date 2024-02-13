package com.whisper.ws.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whisper.ws.auth.token.Credentials;
import com.whisper.ws.auth.token.Token;
import com.whisper.ws.user.repository.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@ConditionalOnProperty(name = "whisper.token-type", havingValue = "jwt")
public class JwtTokenService implements TokenService {

    SecretKey key = Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());

    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Token createToken(User user, Credentials credentials) {
        TokenSubject tokenSubject = new TokenSubject(user.getUserId(), user.getActive());
        try {
            String subject = objectMapper.writeValueAsString(tokenSubject);
            String token  = Jwts.builder().setSubject(Long.toString(user.getUserId())).signWith(key).compact();
            return new Token(token, "Bearer");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public User verifyToken(String authorizationHeader) {
        if(authorizationHeader == null) return null;
        var token = authorizationHeader.split(" ")[1];
        JwtParser parser = Jwts.parser().setSigningKey(key).build();
        try{
            Jws<Claims> claims = parser.parseClaimsJws(token);
            var subject = claims.getBody().getSubject();
            var tokenSubject =  objectMapper.readValue(subject, TokenSubject.class);
            long userId = Long.valueOf(claims.getBody().getSubject());
            User user = new User();
            user.setUserId(userId);
            user.setActive(tokenSubject.active());
            return user;
        }catch (JwtException | JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void logout(String authorizationHeader) {

    }

    public static record TokenSubject(long id, Boolean active){}
}
