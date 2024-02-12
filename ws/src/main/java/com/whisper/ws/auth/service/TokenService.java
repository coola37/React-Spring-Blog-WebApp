package com.whisper.ws.auth.service;

import com.whisper.ws.auth.Credentials;
import com.whisper.ws.auth.token.Token;
import com.whisper.ws.user.repository.entity.*;
import org.springframework.stereotype.Service;

@Service
public interface TokenService {
    public Token createToken(User user, Credentials credentials);
    public User verifyToken(String authorizationHeader);
    public void logout(String authorizationHeader);
}
