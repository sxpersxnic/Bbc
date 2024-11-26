package com.fitcom.fitcom_restapi.utils;

import com.fitcom.fitcom_restapi.exceptions.FailedValidationException;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fitcom.fitcom_restapi.utils.ApplicationConstants.*;

public class RegexChecker {

    public static void checkSignInPrincipal(SignInRequestDTO dto, User user) throws BadCredentialsException {
        String principal = dto.getPrincipal();
        if (Pattern.matches(EMAIL_REGEX, principal)) {
            user.setEmail(principal);
        } else if (Pattern.matches(USERNAME_REGEX, principal)) {
            user.setUsername(principal);
        } else {
            throw new BadCredentialsException("Invalid credentials, user could not be found!");
        }
    }

    public static void validateSignUpCredentials(SignUpRequestDTO dto, User user) throws FailedValidationException {
        Map<String, List<String>> errors = new HashMap<>();

        Matcher emailMatcher = EMAIL_PATTERN.matcher(dto.getEmail());
        Matcher usernameMatcher = USERNAME_PATTERN.matcher(dto.getUsername());
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(dto.getPassword());

        if (!emailMatcher.matches()) {
            errors.put("email", List.of("Invalid email!"));
        }
        if (!usernameMatcher.matches()) {
            errors.put("username", List.of("Invalid username!"));
        }
        if (!passwordMatcher.matches()) {
            errors.put("password", List.of("Invalid password"));
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        } else {
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
        }
    }
}

