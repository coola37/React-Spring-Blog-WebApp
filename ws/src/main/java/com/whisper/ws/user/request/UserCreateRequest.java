package com.whisper.ws.user.request;

import com.whisper.ws.user.controller.validation.UniqueEmail;
import com.whisper.ws.user.repository.entity.User;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class UserCreateRequest {

    @NotBlank
    @Size(min = 4, max = 20)
    private String username;

    @NotBlank
    //@UniqueEmail
    @Email
    private String email;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;
    private String image;

    public User toUser(){
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setUsername(username);
        return user;
    }
}
