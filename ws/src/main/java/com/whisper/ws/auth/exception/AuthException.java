package com.whisper.ws.auth.exception;

import com.whisper.ws.user.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class AuthException extends RuntimeException{
    public AuthException(){
        super(Messages.getMessageForLocale("whisper.auth.invalid.credentials", LocaleContextHolder.getLocale()));
    }
}
