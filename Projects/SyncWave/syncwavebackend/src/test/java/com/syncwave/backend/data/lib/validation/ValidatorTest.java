package com.syncwave.backend.data.lib.validation;

import com.syncwave.backend.lib.exceptions.FailedValidationException;
import com.syncwave.backend.lib.validation.Validator;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.SignInReqDTO;
import com.syncwave.backend.payload.request.SignUpReqDTO;
import com.syncwave.backend.util.DataDTOUtil;
import com.syncwave.backend.util.DataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Validator methods.
 */
public class ValidatorTest {

    private SignUpReqDTO validSignUpReqDTO;
    private SignUpReqDTO invalidSignUpReqDTO;
    private SignInReqDTO validSignInReqDTO_principalIsEmail;
    private SignInReqDTO validSignInReqDTO_principalIsUsername;
    private SignInReqDTO invalidSignInReqDTO;
    private User emptyUser;
    private User user;

    /**
     * Sets up.
     */
    @BeforeEach
    public void setUp() {
        // Set up a SignUpReqDTO with valid credentials
        validSignUpReqDTO = new SignUpReqDTO();
        validSignUpReqDTO.setEmail("valid.email@example.com");
        validSignUpReqDTO.setUsername("ValidUsername123");
        validSignUpReqDTO.setPassword("ValidP@ssw0rd!");

        // Set up a SignInReqDTO with valid credentials

        // Set Principal as valid email
        validSignInReqDTO_principalIsEmail = new SignInReqDTO();
        validSignInReqDTO_principalIsEmail.setPrincipal("valid.email@example.com");
        validSignInReqDTO_principalIsEmail.setPassword("ValidP@ssw0rd!");

        // Set Principal as valid username
        validSignInReqDTO_principalIsUsername = new SignInReqDTO();
        validSignInReqDTO_principalIsUsername.setPassword("ValidP@ssw0rd!");
        validSignInReqDTO_principalIsUsername.setPrincipal("ValidUsername123");

        // SignUpReqDTO with invalid credentials
        invalidSignUpReqDTO = new SignUpReqDTO();
        invalidSignUpReqDTO.setEmail("invalid-email");
        invalidSignUpReqDTO.setUsername("Invalid Username");
        invalidSignUpReqDTO.setPassword("pass");

        // Set Principal as invalid input
        invalidSignInReqDTO = new SignInReqDTO();
        invalidSignInReqDTO.setPrincipal("Invalid %Principal%");
        invalidSignInReqDTO.setPassword("ValidP@ssw0rd!");

        // Set up a new User object
        emptyUser = new User();

        user = DataUtil.getTestUser(0);
    }

    /**
     * Validate credentials with valid dto should not throw exception.
     */
    @Test
    public void validateCredentials_WithValidDto_ShouldNotThrowException() {
        // Act
        Executable executable = () -> Validator.validateCredentials(validSignUpReqDTO, emptyUser);

        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignUpReqDTO.getEmail(), emptyUser.getEmail());
        assertEquals(validSignUpReqDTO.getUsername(), emptyUser.getUsername());
        assertEquals(validSignUpReqDTO.getPassword(), emptyUser.getPassword());
    }

    /**
     * Validate credentials with invalid dto should throw exception.
     */
    @Test
    public void validateCredentials_WithInvalidDto_ShouldThrowException() {
        // Act
        Executable executable = () -> Validator.validateCredentials(invalidSignUpReqDTO, emptyUser);

        // Assert
        FailedValidationException exception = assertThrows(FailedValidationException.class, executable);
        Map<String, List<String>> errors = exception.getErrors();
        assertTrue(errors.containsKey("email"));
        assertTrue(errors.containsKey("username"));
        assertTrue(errors.containsKey("password"));
    }

    /**
     * Set principal when principal is valid email user saves principal as email.
     */
    @Test
    public void setPrincipal_whenPrincipalIsValidEmail_userSavesPrincipalAsEmail(){
        // Act
        Executable executable = () -> Validator.setPrincipal(validSignInReqDTO_principalIsEmail.getPrincipal(), emptyUser);
        emptyUser.setPassword(validSignInReqDTO_principalIsEmail.getPassword());
        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignInReqDTO_principalIsEmail.getPrincipal(), emptyUser.getEmail());
        assertEquals(validSignInReqDTO_principalIsEmail.getPassword(), emptyUser.getPassword());
        assertNull(emptyUser.getUsername());
    }

    /**
     * Set principal when principal is valid username user saves principal as username.
     */
    @Test
    public void setPrincipal_whenPrincipalIsValidUsername_userSavesPrincipalAsUsername(){
        // Act
        Executable executable = () -> Validator.setPrincipal(validSignInReqDTO_principalIsUsername.getPrincipal(), emptyUser);
        emptyUser.setPassword(validSignInReqDTO_principalIsUsername.getPassword());
        // Assert
        assertDoesNotThrow(executable);
        assertEquals(validSignInReqDTO_principalIsUsername.getPrincipal(), emptyUser.getUsername());
        assertEquals(validSignInReqDTO_principalIsUsername.getPassword(), emptyUser.getPassword());
        assertNull(emptyUser.getEmail());
    }

    /**
     * Set principal when principal is invalid throws bad credentials exception.
     */
    @Test
    public void setPrincipal_whenPrincipalIsInvalid_throwsBadCredentialsException(){
        // Act
        Executable executable = () -> Validator.setPrincipal(invalidSignInReqDTO.getPrincipal(), emptyUser);
        emptyUser.setPassword(invalidSignInReqDTO.getPassword());
        // Assert
        assertThrows(BadCredentialsException.class, executable);
    }

    /**
     * Is valid username with valid username should return true.
     */
    @Test
    public void isValidUsername_WithValidUsername_ShouldReturnTrue() {
        // Act
        Boolean isValid = Validator.isValidUsername(validSignUpReqDTO.getUsername());

        // Assert
        assertTrue(isValid);
    }

    /**
     * Is valid username with invalid username should return false.
     */
    @Test
    public void isValidUsername_WithInvalidUsername_ShouldReturnFalse() {
        // Act
        Boolean isValid = Validator.isValidUsername(invalidSignUpReqDTO.getUsername());

        // Assert
        assertFalse(isValid);
    }

    /**
     * Is valid email with valid email should return true.
     */
    @Test
    public void isValidEmail_WithValidEmail_ShouldReturnTrue() {
        // Act
        Boolean isValid = Validator.isValidEmail(validSignUpReqDTO.getEmail());

        // Assert
        assertTrue(isValid);
    }

    /**
     * Is valid email with invalid email should return false.
     */
    @Test
    public void isValidEmail_WithInvalidEmail_ShouldReturnFalse() {
        // Act
        Boolean isValid = Validator.isValidEmail(invalidSignUpReqDTO.getEmail());

        // Assert
        assertFalse(isValid);
    }

    /**
     * Is valid password with valid password should return true.
     */
    @Test
    public void isValidPassword_WithValidPassword_ShouldReturnTrue() {
        // Act
        Boolean isValid = Validator.isValidPassword(validSignUpReqDTO.getPassword());

        // Assert
        assertTrue(isValid);
    }

    /**
     * Is valid password with invalid password should return false.
     */
    @Test
    public void isValidPassword_WithInvalidPassword_ShouldReturnFalse() {
        // Act
        Boolean isValid = Validator.isValidPassword(invalidSignUpReqDTO.getPassword());

        // Assert
        assertFalse(isValid);
    }
}
