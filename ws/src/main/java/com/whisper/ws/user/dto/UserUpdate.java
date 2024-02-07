package com.whisper.ws.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdate(
        @NotBlank(message = "{whisper.constraint.username.notblank}")
        @Size(min = 4, max=255)
        String username,

       // @FileType(types = {"jpeg", "png"})
        String image
) {

}
