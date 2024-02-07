package com.whisper.ws.user.dto;

import com.whisper.ws.user.repository.entity.*;
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
public class UserDTO {
    long userId;

    String username;

    String email;

    String image;
    public UserDTO(User user){
        setUserId(user.getUserId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setImage(user.getImage());
    }

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
