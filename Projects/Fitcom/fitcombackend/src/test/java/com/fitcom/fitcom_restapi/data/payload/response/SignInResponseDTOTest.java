package com.fitcom.fitcom_restapi.data.payload.response;

import com.fitcom.fitcom_restapi.payload.response.SignInResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SignInResponseDTOTest {

    // [x] <------------ CONSTRUCTOR TESTS ------------> //

    @Test
    public void constructor_hasNoArgsConstructor() {
        Class<?> signInResponseDTOClass = SignInResponseDTO.class;
        boolean doesSignInResponseDTOClassHaveEmptyConstructor = Arrays.stream(signInResponseDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesSignInResponseDTOClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasAllArgsConstructor() {
        Class<?> signInResponseDTOClass = SignInResponseDTO.class;
        boolean doesSignInResponseDTOClassHaveAllArgsConstructor = Arrays.stream(signInResponseDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 5);

        assertTrue(doesSignInResponseDTOClassHaveAllArgsConstructor);
    }

    // [x] <------------ FIELD TESTS ------------> //

    @Test
    public void uuidField_doesExist() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredField("uuid"));
    }

    @Test
    public void uuidField_shouldBePrivate() {
        try {
            Field uuidField = SignInResponseDTO.class.getDeclaredField("uuid");
            assertTrue(java.lang.reflect.Modifier.isPrivate(uuidField.getModifiers()), "uuid field should be private");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void uuidField_shouldBeOfTypeUUID() {
        try {
            Field uuidField = SignInResponseDTO.class.getDeclaredField("uuid");
            assertEquals(UUID.class, uuidField.getType(), "uuid field should be of type UUID");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void jwtTokenField_doesExist() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredField("jwtToken"));
    }

    @Test
    public void jwtTokenField_shouldBePrivate() {
        try {
            Field jwtTokenField = SignInResponseDTO.class.getDeclaredField("jwtToken");
            assertTrue(java.lang.reflect.Modifier.isPrivate(jwtTokenField.getModifiers()), "jwtToken field should be private");
        } catch (NoSuchFieldException e) {
            fail("The jwtToken field does not exist");
        }
    }

    @Test
    public void jwtTokenField_shouldBeOfStringType() {
        try {
            Field jwtTokenField = SignInResponseDTO.class.getDeclaredField("jwtToken");
            assertEquals(String.class, jwtTokenField.getType(), "jwtToken field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The jwtToken field does not exist");
        }
    }

    @Test
    public void usernameField_doesExist() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredField("username"));
    }

    @Test
    public void usernameField_shouldBePrivate() {
        try {
            Field usernameField = SignInResponseDTO.class.getDeclaredField("username");
            assertTrue(java.lang.reflect.Modifier.isPrivate(usernameField.getModifiers()), "username field should be private");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void usernameField_shouldBeOfStringType() {
        try {
            Field usernameField = SignInResponseDTO.class.getDeclaredField("username");
            assertEquals(String.class, usernameField.getType(), "username field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredField("email"));
    }

    @Test
    public void emailField_shouldBePrivate() {
        try {
            Field emailField = SignInResponseDTO.class.getDeclaredField("email");
            assertTrue(java.lang.reflect.Modifier.isPrivate(emailField.getModifiers()), "email field should be private");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }

    @Test
    public void emailField_shouldBeOfStringType() {
        try {
            Field emailField = SignInResponseDTO.class.getDeclaredField("email");
            assertEquals(String.class, emailField.getType(), "email field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }

    @Test
    public void roleField_doesExist() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredField("role"));
    }

    @Test
    public void roleField_shouldBePrivate() {
        try {
            Field roleField = SignInResponseDTO.class.getDeclaredField("role");
            assertTrue(java.lang.reflect.Modifier.isPrivate(roleField.getModifiers()), "role field should be private");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }

    @Test
    public void roleField_shouldBeOfStringType() {
        try {
            Field roleField = SignInResponseDTO.class.getDeclaredField("role");
            assertEquals(String.class, roleField.getType(), "role field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }

    // [x] <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUuid", "getJwtToken", "getUsername", "getEmail", "getRole"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredMethod(getterName));
    }

    // [x] <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUuid, java.util.UUID", "setJwtToken, java.lang.String", "setUsername, java.lang.String", "setEmail, java.lang.String", "setRole, java.lang.String"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // [ ] <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyUuid() {
        SignInResponseDTO signInResponseDTO1 = new SignInResponseDTO();
        SignInResponseDTO signInResponseDTO2 = new SignInResponseDTO();

        signInResponseDTO1.setUuid(UUID.randomUUID());
        signInResponseDTO2.setUuid(signInResponseDTO1.getUuid());

        assertEquals(signInResponseDTO1, signInResponseDTO2);
    }

    // [ ] <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> SignInResponseDTO.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesUuid() {
        SignInResponseDTO signInResponseDTO1 = new SignInResponseDTO();
        SignInResponseDTO signInResponseDTO2 = new SignInResponseDTO();

        signInResponseDTO1.setUuid(UUID.randomUUID());
        signInResponseDTO2.setUuid(signInResponseDTO1.getUuid());

        assertEquals(signInResponseDTO1.hashCode(), signInResponseDTO2.hashCode());
    }
}