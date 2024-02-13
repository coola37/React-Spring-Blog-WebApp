package com.whisper.ws.auth.token;

import jakarta.validation.constraints.NotBlank;

public record Credentials(String email, @NotBlank String password){
}
