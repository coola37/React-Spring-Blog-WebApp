package com.whisper.ws.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Boolean active;
    @JsonIgnore
    private String activationToken;
    private String image;
}
