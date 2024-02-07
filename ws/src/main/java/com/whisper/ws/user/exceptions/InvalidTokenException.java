package com.whisper.ws.user.exceptions;

import com.whisper.ws.user.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super(Messages.getMessageForLocale("whisper.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
}
