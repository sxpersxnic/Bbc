package com.syncwave.backend.data.payload.request;

import com.syncwave.backend.payload.request.SignInReqDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignInReqDTOTest {

    private SignInReqDTO signInReqDTO;

    @BeforeEach
    void setUp() {
        signInReqDTO = new SignInReqDTO();
        signInReqDTO.setPrincipal("ValidUsername123");
        signInReqDTO.setPassword("ValidP@ssw0rd!");
    }

    @Test
    void getPrincipal_returnsCorrectPrincipal() {
        assertEquals("ValidUsername123", signInReqDTO.getPrincipal());
    }

    @Test
    void setPrincipal_setsCorrectPrincipal() {
        signInReqDTO.setPrincipal("NewPrincipal");
        assertEquals("NewPrincipal", signInReqDTO.getPrincipal());
    }

    @Test
    void getPassword_returnsCorrectPassword() {
        assertEquals("ValidP@ssw0rd!", signInReqDTO.getPassword());
    }

    @Test
    void setPassword_setsCorrectPassword() {
        signInReqDTO.setPassword("NewP@ssw0rd!");
        assertEquals("NewP@ssw0rd!", signInReqDTO.getPassword());
    }

    @Test
    void equals_returnsTrueForSameObject() {
        assertTrue(signInReqDTO.equals(signInReqDTO));
    }

    @Test
    void equals_returnsFalseForNull() {
        assertFalse(signInReqDTO.equals(null));
    }

    @Test
    void equals_returnsFalseForDifferentClass() {
        assertFalse(signInReqDTO.equals(new Object()));
    }

    @Test
    void equals_returnsTrueForSamePrincipal() {
        SignInReqDTO other = new SignInReqDTO();
        other.setPrincipal(signInReqDTO.getPrincipal());
        assertTrue(signInReqDTO.equals(other));
    }

    @Test
    void hashCode_returnsSameHashCodeForSamePrincipal() {
        SignInReqDTO other = new SignInReqDTO();
        other.setPrincipal(signInReqDTO.getPrincipal());
        assertEquals(signInReqDTO.hashCode(), other.hashCode());
    }
}
