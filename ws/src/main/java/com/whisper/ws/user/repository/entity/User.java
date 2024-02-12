package com.whisper.ws.user.repository.entity;

import com.whisper.ws.auth.token.Token;
import com.whisper.ws.user.controller.validation.UniqueEmail;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    String username;
    String password;
    String email;
    Boolean active = false;
    String activationToken = UUID.randomUUID().toString();
    @Lob
    String image;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    List<Token> tokens;
}
