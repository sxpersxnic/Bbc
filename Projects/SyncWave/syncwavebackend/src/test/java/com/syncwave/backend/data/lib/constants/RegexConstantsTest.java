package com.syncwave.backend.data.lib.constants;

import org.junit.jupiter.api.Test;
import java.util.regex.Matcher;

import static com.syncwave.backend.lib.constants.RegexConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegexConstantsTest {

    @Test
    void emailRegex_isValid() {
        String validEmail = "test@example.com";
        Matcher matcher = EMAIL_PATTERN.matcher(validEmail);
        assertTrue(matcher.matches(), "Expected valid email to match EMAIL_REGEX");
    }

    @Test
    void emailRegex_isInvalid() {
        String invalidEmail = "test@@example..com";
        Matcher matcher = EMAIL_PATTERN.matcher(invalidEmail);
        assertFalse(matcher.matches(), "Expected invalid email to not match EMAIL_REGEX");
    }

    @Test
    void usernameRegex_isValid() {
        String validUsername = "test_user";
        Matcher matcher = USERNAME_PATTERN.matcher(validUsername);
        assertTrue(matcher.matches(), "Expected valid username to match USERNAME_REGEX");
    }

    @Test
    void usernameRegex_isInvalid() {
        String invalidUsername = "invalid & user";
        Matcher matcher = USERNAME_PATTERN.matcher(invalidUsername);
        assertFalse(matcher.matches(), "Expected invalid username to not match USERNAME_REGEX");
    }

    @Test
    void passwordRegex_isValid() {
        String validPassword = "Test@1234";
        Matcher matcher = PASSWORD_PATTERN.matcher(validPassword);
        assertTrue(matcher.matches(), "Expected valid password to match PASSWORD_REGEX");
    }

    @Test
    void passwordRegex_isInvalid() {
        String invalidPassword = "test";
        Matcher matcher = PASSWORD_PATTERN.matcher(invalidPassword);
        assertFalse(matcher.matches(), "Expected invalid password to not match PASSWORD_REGEX");
    }
}