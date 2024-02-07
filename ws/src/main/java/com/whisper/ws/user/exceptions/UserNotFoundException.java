package com.whisper.ws.user.exceptions;

import com.whisper.ws.user.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(Messages.getMessageForLocale("whisper.user.not.found", LocaleContextHolder.getLocale(), id));
    }
}
