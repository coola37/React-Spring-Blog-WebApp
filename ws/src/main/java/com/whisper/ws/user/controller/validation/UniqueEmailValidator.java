package com.whisper.ws.user.controller.validation;

import com.whisper.ws.user.repository.UserRepository;
import com.whisper.ws.user.repository.entity.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserRepository repository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        User inDB = repository.findByEmail(s);
        return inDB == null;
    }
}
