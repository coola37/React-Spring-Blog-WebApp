package com.whisper.ws.auth.response;

import com.whisper.ws.auth.token.Token;
import com.whisper.ws.user.repository.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponse {
    User user;
    Token token;

}
