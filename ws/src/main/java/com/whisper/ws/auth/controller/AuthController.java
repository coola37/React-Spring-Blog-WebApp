package com.whisper.ws.auth.controller;

import com.whisper.ws.auth.service.AuthService;
import com.whisper.ws.auth.token.Credentials;
import com.whisper.ws.auth.response.AuthResponse;
import com.whisper.ws.user.shared.GenericMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    AuthService authService;
    @PostMapping(path = "/api/v1/auth")
    ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials credentials){
        var authResponse = authService.authenticate(credentials);

        var cookie = ResponseCookie.from("w-token", authResponse.getToken().getToken())
                .path("/").httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/api/v1/logout")
    ResponseEntity<GenericMessage> handleLogout(@RequestHeader(name = "Authorization", required = false) String authorizationHeader,
                                                @CookieValue(name = "w-token", required = false) String cookieValue){
        var tokenWithPrefix = authorizationHeader;
        if(cookieValue != null){
            tokenWithPrefix = "AnyPrefix " + cookieValue;
        }
        authService.logout(tokenWithPrefix);
        var cookie = ResponseCookie.from("w-token","")
                .maxAge(0).path("/").httpOnly(true).build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new GenericMessage("Logout success"));
    }


}
