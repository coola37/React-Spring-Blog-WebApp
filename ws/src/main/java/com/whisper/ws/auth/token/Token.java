package com.whisper.ws.auth.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.whisper.ws.user.repository.entity.*;


@Entity
public class Token {

    @Id
    String token;

    @Transient
    String prefix = "Bearer";

    @JsonIgnore
    @ManyToOne
    User user;

    public Token(String token, String prefix) {
        this.token = token;
        this.prefix = prefix;
    }
    public Token() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
