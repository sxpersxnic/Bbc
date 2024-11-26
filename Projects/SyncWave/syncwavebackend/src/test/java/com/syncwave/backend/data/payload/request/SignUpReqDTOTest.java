package com.syncwave.backend.data.payload.request;

import com.syncwave.backend.payload.request.SignUpReqDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignUpReqDTOTest {

    private SignUpReqDTO signUpReqDTO;

    @BeforeEach
    void setUp() {
        signUpReqDTO = new SignUpReqDTO();
        signUpReqDTO.setUsername("ValidUsername123");
        signUpReqDTO.setEmail("valid.email@example.com");
        signUpReqDTO.setPassword("ValidP@ssw0rd!");
        signUpReqDTO.setConfirmPassword("ValidP@ssw0rd!");
    }

    @Test
    void getUsername_returnsCorrectUsername() {
        assertEquals("ValidUsername123", signUpReqDTO.getUsername());
    }

    @Test
    void setUsername_setsCorrectUsername() {
        signUpReqDTO.setUsername("NewUsername");
        assertEquals("NewUsername", signUpReqDTO.getUsername());
    }

    @Test
    void getEmail_returnsCorrectEmail() {
        assertEquals("valid.email@example.com", signUpReqDTO.getEmail());
    }

    @Test
    void setEmail_setsCorrectEmail() {
        signUpReqDTO.setEmail("new.email@example.com");
        assertEquals("new.email@example.com", signUpReqDTO.getEmail());
    }

    @Test
    void getPassword_returnsCorrectPassword() {
        assertEquals("ValidP@ssw0rd!", signUpReqDTO.getPassword());
    }

    @Test
    void setPassword_setsCorrectPassword() {
        signUpReqDTO.setPassword("NewP@ssw0rd!");
        assertEquals("NewP@ssw0rd!", signUpReqDTO.getPassword());
    }

    @Test
    void getConfirmPassword_returnsCorrectConfirmPassword() {
        assertEquals("ValidP@ssw0rd!", signUpReqDTO.getConfirmPassword());
    }

    @Test
    void setConfirmPassword_setsCorrectConfirmPassword() {
        signUpReqDTO.setConfirmPassword("NewP@ssw0rd!");
        assertEquals("NewP@ssw0rd!", signUpReqDTO.getConfirmPassword());
    }

    @Test
    void equals_returnsTrueForSameObject() {
        assertTrue(signUpReqDTO.equals(signUpReqDTO));
    }

    @Test
    void equals_returnsFalseForNull() {
        assertFalse(signUpReqDTO.equals(null));
    }

    @Test
    void equals_returnsFalseForDifferentClass() {
        assertFalse(signUpReqDTO.equals(new Object()));
    }

    @Test
    void equals_returnsTrueForSameUsername() {
        SignUpReqDTO other = new SignUpReqDTO();
        other.setUsername(signUpReqDTO.getUsername());
        assertTrue(signUpReqDTO.equals(other));
    }

    @Test
    void hashCode_returnsSameHashCodeForSameUsername() {
        SignUpReqDTO other = new SignUpReqDTO();
        other.setUsername(signUpReqDTO.getUsername());
        assertEquals(signUpReqDTO.hashCode(), other.hashCode());
    }
}

