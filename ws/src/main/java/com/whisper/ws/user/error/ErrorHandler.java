package com.whisper.ws.user.error;

import com.whisper.ws.auth.exception.AuthException;
import com.whisper.ws.user.exceptions.*;
import com.whisper.ws.user.response.ApiError;
import com.whisper.ws.user.shared.Messages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            NotUniqueEmailException.class,
            ActivationNotificationException.class,
            InvalidTokenException.class,
            UserNotFoundException.class,
            AuthException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleMethodArgNotValidEx(Exception exception, HttpServletRequest request) {

        ApiError error = ApiError.builder()
                .status(400)
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .validationErrors(null)
                .build();
        if(exception instanceof MethodArgumentNotValidException){
            String message = Messages.getMessageForLocale("whisper.error.validation",
                    LocaleContextHolder.getLocale());
            error.setMessage(message);
            error.setStatus(400);
            var validationErrors = ((MethodArgumentNotValidException)exception).getBindingResult().getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage,
                            (existing, replacing) -> existing ));
            error.setValidationErrors(validationErrors);
        }else if(exception instanceof NotUniqueEmailException){
            error.setStatus(400);
            Map<String, String> validationErrors = new HashMap<>();
            validationErrors.put("E-mail", "E-mail in use ");
            error.setValidationErrors(validationErrors);
        }else if(exception instanceof ActivationNotificationException){
            error.setStatus(502);
        }else if(exception instanceof InvalidTokenException) {
            error.setStatus(400);
        }else if(exception instanceof UserNotFoundException){
            error.setStatus(404);
        }else if(exception instanceof AuthException){
            error.setStatus(401);
        }
        return ResponseEntity.status(error.getStatus()).body(error);
    }
}
