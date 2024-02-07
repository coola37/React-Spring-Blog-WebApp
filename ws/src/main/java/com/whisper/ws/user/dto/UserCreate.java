package com.whisper.ws.user.dto;

import com.whisper.ws.user.controller.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.whisper.ws.user.repository.entity.*;
public record UserCreate(
        @NotBlank(message = "{whisper.constraint.username.notblank}")
        @Size(min = 4, max=255)
        String username,

        @NotBlank
        @Email
        @UniqueEmail
        String email,

        @Size(min = 8, max=255)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{whisper.constraint.password.pattern}")
        String password
) {

    public User toUser(){
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

}
