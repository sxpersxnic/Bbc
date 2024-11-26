package com.fitcom.fitcom_restapi.data.utils;

import com.fitcom.fitcom_restapi.exceptions.FailedValidationException;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;
import com.fitcom.fitcom_restapi.utils.RegexChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RegexCheckerTest {

    private SignUpRequestDTO validSignUpRequestDTO;
    private SignUpRequestDTO invalidSignUpRequestDTO;
    private SignInRequestDTO validSignInRequestDTO_principalIsEmail;
    private SignInRequestDTO validSignInRequestDTO_principalIsUsername;
    private SignInRequestDTO invalidSignInRequestDTO;
    private User user;



    @BeforeEach
    public void setUp() {
        // Set up a SignUpRequestDTO with valid credentials
        validSignUpRequestDTO = new SignUpRequestDTO();
        validSignUpRequestDTO.setEmail("valid.email@example.com");
        validSignUpRequestDTO.setUsername("ValidUsername123");
        validSignUpRequestDTO.setPassword("ValidP@ssw0rd!");

        // Set up a SignInRequestDTO with valid credentials

        // Set Principal as valid email
        validSignInRequestDTO_principalIsEmail = new SignInRequestDTO();
        validSignInRequestDTO_principalIsEmail.setPrincipal("valid.email@example.com");
        validSignInRequestDTO_principalIsEmail.setPassword("ValidP@ssw0rd!");

        // Set Principal as valid username
        validSignInRequestDTO_principalIsUsername = new SignInRequestDTO();
        validSignInRequestDTO_principalIsUsername.setPassword("ValidP@ssw0rd!");
        validSignInRequestDTO_principalIsUsername.setPrincipal("ValidUsername123");

        // SignUpRequestDTO with invalid credentials
        invalidSignUpRequestDTO = new SignUpRequestDTO();
        invalidSignUpRequestDTO.setEmail("invalid-email");
        invalidSignUpRequestDTO.setUsername("Invalid Username");
        invalidSignUpRequestDTO.setPassword("pass");

        // Set Principal as invalid input
        invalidSignInRequestDTO = new SignInRequestDTO();
        invalidSignInRequestDTO.setPrincipal("Invalid %Principal%");
        invalidSignInRequestDTO.setPassword("ValidP@ssw0rd!");

        // Set up a new User object
        user = new User();
    }

    @Test
    public void validateSignUpCredentials_WithValidDto_ShouldNotThrowException() {
        // Act
        Executable executable = () -> RegexChecker.validateSignUpCredentials(validSignUpRequestDTO, user);

        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignUpRequestDTO.getEmail(), user.getEmail());
        assertEquals(validSignUpRequestDTO.getUsername(), user.getUsername());
        assertEquals(validSignUpRequestDTO.getPassword(), user.getPassword());
    }

    @Test
    public void validateSignUpCredentials_WithInvalidDto_ShouldThrowException() {
        // Act
        Executable executable = () -> RegexChecker.validateSignUpCredentials(invalidSignUpRequestDTO, user);

        // Assert
        FailedValidationException exception = assertThrows(FailedValidationException.class, executable);
        Map<String, List<String>> errors = exception.getErrors();
        assertTrue(errors.containsKey("email"));
        assertTrue(errors.containsKey("username"));
        assertTrue(errors.containsKey("password"));
    }

    @Test
    public void checkSignInPrincipals_whenPrincipalIsValidEmail_userSavesPrincipalAsEmail(){
        // Act
        Executable executable = () -> RegexChecker.checkSignInPrincipal(validSignInRequestDTO_principalIsEmail, user);
        user.setPassword(validSignInRequestDTO_principalIsEmail.getPassword());
        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignInRequestDTO_principalIsEmail.getPrincipal(), user.getEmail());
        assertEquals(validSignInRequestDTO_principalIsEmail.getPassword(), user.getPassword());
        assertNull(user.getUsername());
    }

    @Test
    public void checkSignInPrincipals_whenPrincipalIsValidUsername_userSavesPrincipalAsUsername(){
        // Act
        Executable executable = () -> RegexChecker.checkSignInPrincipal(validSignInRequestDTO_principalIsUsername, user);
        user.setPassword(validSignInRequestDTO_principalIsUsername.getPassword());

        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignInRequestDTO_principalIsUsername.getPrincipal(), user.getUsername());
        assertEquals(validSignInRequestDTO_principalIsUsername.getPassword(), user.getPassword());
        assertNull(user.getEmail());
    }

    @Test
    public void checkSignInPrincipal_WithInvalidPrincipal_ShouldThrowException() {
        // Act
        Executable executable = () -> RegexChecker.checkSignInPrincipal(invalidSignInRequestDTO, user);

        // Assert
        assertThrows(BadCredentialsException.class, executable);
        assertNull(user.getEmail());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }
}

