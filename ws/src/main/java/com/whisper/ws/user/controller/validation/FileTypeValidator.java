package com.whisper.ws.user.controller.validation;

import com.whisper.ws.FileService;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {

    @Autowired
    FileService fileService;

    String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isEmpty()) return true;
        String type = fileService.detectType(s);
        for(String validTypes: types){
            if(type.contains(validTypes)) return true;
        }
        //if(type.equals("image/jpeg") || type.equals("image/png")) return true;

        String validTypes = Arrays.stream(types).collect(Collectors.joining(", "));
        constraintValidatorContext.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = constraintValidatorContext
                .unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", validTypes);
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                .addConstraintViolation();

        return false;
    }

}
