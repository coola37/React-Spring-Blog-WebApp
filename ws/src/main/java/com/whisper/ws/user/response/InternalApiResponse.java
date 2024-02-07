package com.whisper.ws.user.response;

import com.whisper.ws.user.shared.GenericMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class InternalApiResponse <T>{
    private GenericMessage message;
    private T payload;
    private boolean hasError;
    private HttpStatus httpStatus;
}
