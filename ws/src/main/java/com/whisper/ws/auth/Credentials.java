package com.whisper.ws.auth;

import jakarta.validation.constraints.NotBlank;

public record Credentials(String email, @NotBlank String password){
}
