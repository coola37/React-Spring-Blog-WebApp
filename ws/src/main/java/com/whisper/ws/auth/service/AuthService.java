package com.whisper.ws.auth.service;

import com.whisper.ws.auth.token.Credentials;
import com.whisper.ws.auth.exception.AuthException;
import com.whisper.ws.auth.response.AuthResponse;
import com.whisper.ws.auth.token.Token;
import com.whisper.ws.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.whisper.ws.user.repository.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;
    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userService.findByEmail(credentials.email());
        if(inDB == null) throw new AuthException();
        if(!passwordEncoder.matches(credentials.password(), inDB.getPassword())) throw new AuthException();

        Token token = tokenService.createToken(inDB, credentials);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(inDB);
        return authResponse;
    }

    public void logout(String authorizationHeader) {
        tokenService.logout(authorizationHeader);
    }
}
