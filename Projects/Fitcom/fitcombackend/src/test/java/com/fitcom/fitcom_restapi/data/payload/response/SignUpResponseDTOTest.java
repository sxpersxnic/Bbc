package com.fitcom.fitcom_restapi.data.payload.response;

import com.fitcom.fitcom_restapi.payload.response.SignUpResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SignUpResponseDTOTest {

    // [x] <------------ CONSTRUCTOR TESTS ------------> //

    @Test
    public void constructor_hasNoArgsConstructor() {
        Class<?> signUpResponseDTOClass = SignUpResponseDTO.class;
        boolean doesSignUpResponseDTOClassHaveEmptyConstructor = Arrays.stream(signUpResponseDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesSignUpResponseDTOClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasAllArgsConstructor() {
        Class<?> signUpResponseDTOClass = SignUpResponseDTO.class;
        boolean doesSignUpResponseDTOClassHaveAllArgsConstructor = Arrays.stream(signUpResponseDTOClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 5);

        assertTrue(doesSignUpResponseDTOClassHaveAllArgsConstructor);
    }

    // [x] <------------ FIELD TESTS ------------> //

    @Test
    public void uuidField_doesExist() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredField("uuid"));
    }

    @Test
    public void uuidField_shouldBePrivate() {
        try {
            Field uuidField = SignUpResponseDTO.class.getDeclaredField("uuid");
            assertTrue(java.lang.reflect.Modifier.isPrivate(uuidField.getModifiers()), "uuid field should be private");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void uuidField_shouldBeOfTypeUUID() {
        try {
            Field uuidField = SignUpResponseDTO.class.getDeclaredField("uuid");
            assertEquals(UUID.class, uuidField.getType(), "uuid field should be of type UUID");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void usernameField_doesExist() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredField("username"));
    }

    @Test
    public void usernameField_shouldBePrivate() {
        try {
            Field usernameField = SignUpResponseDTO.class.getDeclaredField("username");
            assertTrue(java.lang.reflect.Modifier.isPrivate(usernameField.getModifiers()), "username field should be private");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void usernameField_shouldBeOfTypeString() {
        try {
            Field usernameField = SignUpResponseDTO.class.getDeclaredField("username");
            assertEquals(String.class, usernameField.getType(), "username field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredField("email"));
    }

    @Test
    public void emailField_shouldBePrivate() {
        try {
            Field emailField = SignUpResponseDTO.class.getDeclaredField("email");
            assertTrue(java.lang.reflect.Modifier.isPrivate(emailField.getModifiers()), "email field should be private");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }

    @Test
    public void emailField_shouldBeOfTypeString() {
        try {
            Field emailField = SignUpResponseDTO.class.getDeclaredField("email");
            assertEquals(String.class, emailField.getType(), "email field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }

    @Test
    public void createTimeField_doesExist() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredField("createTime"));
    }

    @Test
    public void createTimeField_shouldBePrivate() {
        try {
            Field createTimeField = SignUpResponseDTO.class.getDeclaredField("createTime");
            assertTrue(java.lang.reflect.Modifier.isPrivate(createTimeField.getModifiers()), "createTime field should be private");
        } catch (NoSuchFieldException e) {
            fail("The createTime field does not exist");
        }
    }

    @Test
    public void createTimeField_shouldBeOfTypeInstant() {
        try {
            Field createTimeField = SignUpResponseDTO.class.getDeclaredField("createTime");
            assertEquals(Instant.class, createTimeField.getType(), "createTime field should be of type Instant");
        } catch (NoSuchFieldException e) {
            fail("The createTime field does not exist");
        }
    }

    @Test
    public void roleField_doesExist() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredField("role"));
    }

    @Test
    public void roleField_shouldBePrivate() {
        try {
            Field roleField = SignUpResponseDTO.class.getDeclaredField("role");
            assertTrue(java.lang.reflect.Modifier.isPrivate(roleField.getModifiers()), "role field should be private");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }

    @Test
    public void roleField_shouldBeOfTypeString() {
        try {
            Field roleField = SignUpResponseDTO.class.getDeclaredField("role");
            assertEquals(String.class, roleField.getType(), "role field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }

    // [x] <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUuid", "getEmail", "getUsername", "getCreateTime", "getRole"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredMethod(getterName));
    }

    // [x] <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUuid, java.util.UUID", "setUsername, java.lang.String", "setEmail, java.lang.String", "setCreateTime, java.time.Instant", "setRole, java.lang.String"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // [ ] <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyUuid() {
        SignUpResponseDTO signUpResponseDTO1 = new SignUpResponseDTO();
        SignUpResponseDTO signUpResponseDTO2 = new SignUpResponseDTO();

        signUpResponseDTO1.setUuid(UUID.randomUUID());
        signUpResponseDTO2.setUuid(signUpResponseDTO1.getUuid());

        signUpResponseDTO1.setEmail("NotSameEmail@foo.bar");
        signUpResponseDTO1.setUsername("NotSameUsername");
        signUpResponseDTO1.setCreateTime(Instant.now());
        signUpResponseDTO1.setRole("NotSameRole");

        assertEquals(signUpResponseDTO1, signUpResponseDTO2);
    }

    // [ ] <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> SignUpResponseDTO.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesUuid() {
        SignUpResponseDTO signUpResponseDTO1 = new SignUpResponseDTO();
        SignUpResponseDTO signUpResponseDTO2 = new SignUpResponseDTO();

        signUpResponseDTO1.setUuid(UUID.randomUUID());
        signUpResponseDTO2.setUuid(signUpResponseDTO1.getUuid());

        assertEquals(signUpResponseDTO1.hashCode(), signUpResponseDTO2.hashCode());
    }
}