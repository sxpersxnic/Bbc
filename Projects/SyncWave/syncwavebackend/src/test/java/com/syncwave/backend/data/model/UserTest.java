package com.syncwave.backend.data.model;

import com.syncwave.backend.model.User;
import com.syncwave.backend.util.DataUtil;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;

import static com.syncwave.backend.util.TestConstants.ID;
import static com.syncwave.backend.util.TestConstants.USERNAME;
import static com.syncwave.backend.util.TestConstants.EMAIL;
import static com.syncwave.backend.util.TestConstants.PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user1;
    private User user2;
    private final Class<User> clazz = User.class;

    @BeforeEach
    void setUp() {
        user1 = DataUtil.getTestUser(0);
        user2 = DataUtil.getTestUser(0);
    }

    // Annotation tests
    @Test
    void class_isAnnotatedWithEntity() {
        assertNotNull(clazz.getDeclaredAnnotation(Entity.class));
    }

    @Test
    void idField_isAnnotatedWithId() {
        try {
            assertNotNull(clazz.getDeclaredField(ID).getDeclaredAnnotation(Id.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void idField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(clazz.getDeclaredField(ID).getDeclaredAnnotation(GeneratedValue.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void idFieldGeneratedValueAnnotation_isStrategyIDENTITY() {
        try {
            assertEquals(GenerationType.IDENTITY, clazz
                    .getDeclaredField(ID)
                    .getDeclaredAnnotation(GeneratedValue.class)
                    .strategy()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void idField_isAnnotatedWithColumn() {
        try {
            assertNotNull(clazz
                    .getDeclaredField(ID)
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void idFieldColumnAnnotation_isNameUuid() {
        try {
            assertEquals(ID, clazz
                    .getDeclaredField(ID)
                    .getDeclaredAnnotation(Column.class)
                    .name()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void idFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(clazz
                    .getDeclaredField(ID)
                    .getDeclaredAnnotation(Column.class)
                    .nullable());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void usernameField_isAnnotatedWithColumn() {
        try {
            assertNotNull(clazz
                    .getDeclaredField(USERNAME)
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameFieldColumnAnnotation_isNameUsername() {
        try {
            assertEquals(USERNAME, clazz.getDeclaredField(USERNAME).getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(clazz.getDeclaredField(USERNAME).getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void usernameFieldColumnAnnotation_isUniqueTrue() {
        try {
            assertTrue(clazz.getDeclaredField(USERNAME).getDeclaredAnnotation(Column.class).unique());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailField_isAnnotatedWithColumn() {
        try {
            assertNotNull(clazz.getDeclaredField(EMAIL).getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isNameEmail() {
        try {
            assertEquals(EMAIL, clazz.getDeclaredField(EMAIL).getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(clazz.getDeclaredField(EMAIL).getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isUniqueTrue() {
        try {
            assertTrue(clazz.getDeclaredField(EMAIL).getDeclaredAnnotation(Column.class).unique());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void passwordField_isAnnotatedWithColumn() {
        try {
            assertNotNull(clazz.getDeclaredField(PASSWORD).getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {
            fail();

        }}

    @Test
    public void passwordFieldColumnAnnotation_isNamePassword() {
        try {
            assertEquals(PASSWORD, clazz.getDeclaredField(PASSWORD).getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();

        }}

    @Test
    public void passwordFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(clazz.getDeclaredField(PASSWORD).getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    // Constructor tests
    /**
     * Tests if {@link User} has no args constructor
     * <p>
     * No args constructor: {@code public User() {}}
     */
    @Test
    void constructor_hasNoArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor());
    }

    /**
     * Tests if {@link User} has all args constructor.
     * <p>
     * All args constructor:
     * <p>
     * {@code public User(Long id, String username, String email, String password) {
     * this.id = id; this.username = username; this.email = email; this.password = password;
     * }
     * }
     */
    @Test
    void constructor_hasAllArgsConstructor() {
        assertDoesNotThrow(() -> clazz.getDeclaredConstructor(Long.class, String.class, String.class, String.class));
    }

    // Field tests
    /**
     * Tests if {@link User} field id does exist, is {@code private} and has datatype {@link Long}:
     * <p>
     * {@code private Long id}
     */
    @Test
    void idField_doesExist() throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(ID);
        assertEquals("Long", f.getType().getSimpleName());
        assertTrue((f.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
        assertDoesNotThrow(() -> clazz.getDeclaredField(ID));
    }
    /**
     * Tests if {@link User} field username does exist, is {@code private} and has datatype {@link String}:
     * <p>
     * {@code private String username}
     */
    @Test
    void usernameField_doesExist() throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(USERNAME);
        assertEquals("String", f.getType().getSimpleName());
        assertTrue((f.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
        assertDoesNotThrow(() -> clazz.getDeclaredField(USERNAME));
    }
    /**
     * Tests if {@link User} field email does exist, is {@code private} and has datatype {@link String}:
     * <p>
     * {@code private String email}
     */
    @Test
    void emailField_doesExist() throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(EMAIL);
        assertEquals("String", f.getType().getSimpleName());
        assertTrue((f.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
        assertDoesNotThrow(() -> clazz.getDeclaredField(EMAIL));
    }
    /**
     * Tests if {@link User} field password does exist, is {@code private} and has datatype {@link String}:
     * <p>
     * {@code private String password}
     */
    @Test
    void passwordField_doesExist() throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(PASSWORD);
        assertEquals("String", f.getType().getSimpleName());
        assertTrue((f.getModifiers() & java.lang.reflect.Modifier.PRIVATE) != 0);
        assertDoesNotThrow(() -> clazz.getDeclaredField(PASSWORD));
    }

    // Getter tests
    @ParameterizedTest
    @CsvSource(value = {"getId", "getEmail", "getUsername", "getPassword"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(getterName));
    }

    // Setter tests
    @ParameterizedTest
    @CsvSource(value = {"setId, java.lang.Long", "setUsername, java.lang.String", "setEmail, java.lang.String", "setPassword, java.lang.String"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // Equals tests
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyId() {
        user1.setEmail("NotSameEmail@foo.bar");
        user1.setUsername("NotSameUsername");
        user1.setPassword("N0tS@mePassword");

        assertEquals(user1, user2);
    }

    // HashCode tests
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesId() {
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}
