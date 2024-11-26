package com.fitcom.fitcom_restapi.data.payload.request;

import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;
import com.fitcom.fitcom_restapi.util.DataDTOUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SignUpRequestDTOTest {

    private SignUpRequestDTO dto;
    private SignUpRequestDTO other;
    private final Class<SignUpRequestDTO> clazz = SignUpRequestDTO.class;

    @BeforeEach
    void setUp() {
        dto = DataDTOUtil.getTestSignUpRequestDTO();
        other = DataDTOUtil.getTestSignUpRequestDTO();
    }

    //: <------------ FIELD TESTS ------------> //
    @Test
    public void usernameField_doesExist() {
        assertDoesNotThrow(() -> clazz.getDeclaredField("username"));
    }

    @Test
    void usernameField_isPrivate() throws NoSuchFieldException {
        Field usernameField = clazz.getDeclaredField("username");
        assertTrue((usernameField.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
    }

    @Test
    void usernameField_hasTypeString() throws NoSuchFieldException {
        Field usernameField = clazz.getDeclaredField("username");
        assertEquals("String", usernameField.getType().getSimpleName());
    }

    @Test
    public void usernameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("username")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameField_isAnnotatedWithPattern() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("username")
                    .getDeclaredAnnotation(Pattern.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void usernameField_patternAnnotationHasValidRegex() {
        assertDoesNotThrow(() -> java.util.regex.Pattern.compile(clazz.getDeclaredField("username").getDeclaredAnnotation(Pattern.class).regexp()));
    }

    //: Email
    @Test
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> clazz.getDeclaredField("email"));
    }

    @Test
    void emailField_isPrivate() throws NoSuchFieldException {
        Field emailField = clazz.getDeclaredField("email");
        assertTrue((emailField.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
    }

    @Test
    void emailField_hasTypeString() throws NoSuchFieldException {
        Field emailField = clazz.getDeclaredField("email");
        assertEquals("String", emailField.getType().getSimpleName());
    }

    @Test
    public void emailField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void emailField_isAnnotatedWithPattern() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(Pattern.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void emailField_patternAnnotationHasValidRegex() {
        assertDoesNotThrow(() -> java.util.regex.Pattern.compile(clazz.getDeclaredField("email").getDeclaredAnnotation(Pattern.class).regexp()));
    }


    //: Password
    @Test
    public void passwordField_doesExist() {
        assertDoesNotThrow(() -> clazz.getDeclaredField("password"));
    }

    @Test
    void passwordField_isPrivate() throws NoSuchFieldException {
        Field passwordField = clazz.getDeclaredField("password");
        assertTrue((passwordField.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
    }

    @Test
    void passwordField_hasTypeString() throws NoSuchFieldException {
        Field passwordField = clazz.getDeclaredField("password");
        assertEquals("String", passwordField.getType().getSimpleName());
    }

    @Test
    public void passwordField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("password")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void passwordField_isAnnotatedWithPattern() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("password")
                    .getDeclaredAnnotation(Pattern.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void passwordField_patternAnnotationHasValidRegex() {
        assertDoesNotThrow(() -> java.util.regex.Pattern.compile(clazz.getDeclaredField("password").getDeclaredAnnotation(Pattern.class).regexp()));
    }

    //: Role
    @Test
    public void roleField_doesExist() {
        assertDoesNotThrow(() -> clazz.getDeclaredField("role"));
    }

    @Test
    void roleField_isPrivate() throws NoSuchFieldException {
        Field usernameField = clazz.getDeclaredField("role");
        assertTrue((usernameField.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
    }

    @Test
    void roleField_hasTypeString() throws NoSuchFieldException {
        Field usernameField = clazz.getDeclaredField("role");
        assertEquals("String", usernameField.getType().getSimpleName());
    }

    @Test
    void class_shouldHaveNoArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor());
    }

    @Test
    void class_shouldHaveConstructorWithoutRole() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class, String.class, String.class));
    }

    @Test
    void class_shouldHaveAllArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class, String.class, String.class, String.class));
    }

    //: <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUsername", "getEmail", "getPassword", "getRole"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(getterName));
    }

    @Test
    void getters_shouldRetrieveFieldValues() {
        SignUpRequestDTO expected = DataDTOUtil.getTestSignUpRequestDTO();
        assertEquals(expected.getUsername(), dto.getUsername());
        assertEquals(expected.getEmail(), dto.getEmail());
        assertEquals(expected.getPassword(), dto.getPassword());
    }

    //: <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUsername, java.lang.String", "setEmail, java.lang.String", "setPassword, java.lang.String", "setRole, java.lang.String"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    @Test
    void setters_shouldSetFieldValues() {
        dto.setUsername("NewUser");
        assertEquals("NewUser", dto.getUsername());
        dto.setEmail("new@example.com");
        assertEquals("new@example.com", dto.getEmail());
        dto.setPassword("NewPassword@123");
        assertEquals("NewPassword@123", dto.getPassword());
    }

    //: <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("equals", Object.class));
    }

    @Test
    void equals_shouldBeReflexive() {
        SignUpRequestDTO other = DataDTOUtil.getTestSignUpRequestDTO();
        assertEquals(other, dto);
    }

    @Test
    void equals_shouldBeSymmetric() {
        assertTrue(dto.equals(other) && other.equals(dto));
    }

    @Test
    void equals_shouldBeTransitive() {
        SignUpRequestDTO first = new SignUpRequestDTO("TestUser", "test@example.com", "Password@123");
        SignUpRequestDTO second = new SignUpRequestDTO("TestUser", "test@example.com", "Password@123");
        SignUpRequestDTO third = new SignUpRequestDTO("TestUser", "test@example.com", "Password@123");
        assertTrue(first.equals(second) && second.equals(third) && first.equals(third));
    }

    @Test
    void equals_shouldBeConsistent() {
        assertEquals(dto, other);
        assertEquals(dto, other);
    }

    @Test
    void equals_shouldReturnFalseOnNull() {
        assertFalse(dto.equals(null));
    }

    //: <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("hashCode"));
    }

    @Test
    void hashCode_shouldBeConsistent() {
        int initialHashCode = dto.hashCode();
        assertEquals(initialHashCode, dto.hashCode());
    }

    @Test
    void hashCode_shouldBeEqualForEqualObjects() {
        assertEquals(dto.hashCode(), other.hashCode());
    }
}
