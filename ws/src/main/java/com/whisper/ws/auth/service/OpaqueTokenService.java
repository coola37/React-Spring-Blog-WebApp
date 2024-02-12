package com.whisper.ws.auth.service;

import com.whisper.ws.auth.Credentials;
import com.whisper.ws.auth.service.TokenService;
import com.whisper.ws.auth.token.Token;
import com.whisper.ws.auth.token.TokenRepository;
import com.whisper.ws.user.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@ConditionalOnProperty(value = "whisper.token-type", havingValue = "opaque")
public class OpaqueTokenService implements TokenService {

    @Autowired
    TokenRepository tokenRepository;
    public Token createToken(User user, Credentials credentials) {
        String tokenId = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(tokenId);
        token.setUser(user);
        return tokenRepository.save(token);
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        var tokenInDb = getToken(authorizationHeader);
        if(tokenInDb.isPresent()) return tokenInDb.get().getUser();
        else return null;
    }

    @Override
    public void logout(String authorizationHeader) {
        var tokenInDb = getToken(authorizationHeader);
        if(tokenInDb.isPresent()) tokenRepository.delete(tokenInDb.get());
        else return;
    }

    private Optional<Token> getToken(String authorizationHeader){
        if(authorizationHeader == null) return Optional.empty();
        else{
            var token  = authorizationHeader.split(" ")[1];
            var tokenInDb = tokenRepository.findById(token);
            return tokenRepository.findById(token);
        }

    }
}
