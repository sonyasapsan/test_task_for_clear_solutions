package com.test_task.project.validation.email;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final Pattern PATTERN_OF_ISBN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && PATTERN_OF_ISBN.matcher(email).matches();
    }
}