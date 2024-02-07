package com.whisper.ws.auth.controller;

import com.whisper.ws.auth.service.AuthService;
import com.whisper.ws.auth.Credentials;
import com.whisper.ws.auth.exception.AuthException;
import com.whisper.ws.auth.response.AuthResponse;
import com.whisper.ws.user.response.ApiError;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping(path = "/api/v1/auth")
    AuthResponse handleAuthentication(@Valid @RequestBody Credentials credentials){

       return authService.authenticate(credentials);
    }


}
