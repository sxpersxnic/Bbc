package com.syncwave.backend.lib.validation.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.syncwave.backend.lib.constants.RegexConstants.PASSWORD_REGEX;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        return value.matches(PASSWORD_REGEX);
    }
}