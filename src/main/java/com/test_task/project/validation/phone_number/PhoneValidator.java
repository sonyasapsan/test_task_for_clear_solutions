package com.test_task.project.validation.phone_number;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private static final Pattern PATTERN_OF_ISBN = Pattern.compile("^\\+(?:[0-9] ?){6,14}[0-9]$");

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return phoneNumber != null && PATTERN_OF_ISBN.matcher(phoneNumber).matches();
    }
}
