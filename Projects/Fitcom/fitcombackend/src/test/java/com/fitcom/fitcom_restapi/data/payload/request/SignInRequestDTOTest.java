package com.fitcom.fitcom_restapi.data.payload.request;

import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.util.DataDTOUtil;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class SignInRequestDTOTest {

    private SignInRequestDTO dto;
    private SignInRequestDTO other;
    private final Class<SignInRequestDTO> clazz = SignInRequestDTO.class;

    @BeforeEach
    void setUp() {
        dto = DataDTOUtil.getTestSignInRequestDTO();
        other = DataDTOUtil.getTestSignInRequestDTO();
    }

    //: <------------ FIELD TESTS ------------> //

    //: >--Principal--<
    @Test
    public void principalField_doesExist() {
        assertDoesNotThrow(() -> clazz.getDeclaredField("principal"));
    }

    @Test
    void principalField_isPrivate() throws NoSuchFieldException {
        Field principalField = clazz.getDeclaredField("principal");
        assertTrue((principalField.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
    }

    @Test
    void principalField_hasTypeString() throws NoSuchFieldException {
        Field principalField = clazz.getDeclaredField("principal");
        assertEquals("String", principalField.getType().getSimpleName());
    }

    @Test
    public void principalField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(clazz
                    .getDeclaredField("principal")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    //: >--Password--<
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
    void class_shouldHaveNoArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor());
    }

    @Test
    void class_shouldHaveAllArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor(String.class, String.class));
    }

    //: <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getPrincipal", "getPassword"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(getterName));
    }

    @Test
    void getters_shouldRetrieveFieldValues() {
        assertEquals(other.getPrincipal(), dto.getPrincipal());
        assertEquals(other.getPassword(), dto.getPassword());
    }

    //: <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setPrincipal, java.lang.String", "setPassword, java.lang.String"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    @Test
    void setters_shouldSetFieldValues() {
        dto.setPrincipal("NewUser");
        assertEquals("NewUser", dto.getPrincipal());
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
        assertEquals(other, dto);
    }

    @Test
    void equals_shouldBeSymmetric() {
        assertTrue(dto.equals(other) && other.equals(dto));
    }

    @Test
    void equals_shouldBeTransitive() {
        assertTrue(dto.equals(other) && other.equals(dto));
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
