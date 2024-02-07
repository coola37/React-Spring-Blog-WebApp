package com.whisper.ws.user.exceptions;

import com.whisper.ws.user.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class ActivationNotificationException extends RuntimeException{

    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("whisper.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
}
