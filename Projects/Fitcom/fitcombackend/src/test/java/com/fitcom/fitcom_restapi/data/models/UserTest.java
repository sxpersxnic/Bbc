package com.fitcom.fitcom_restapi.data.models;

import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.util.DataUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    // [x] <------------ CONSTRUCTOR TESTS ------------> //

    @Test
    public void constructor_hasNoArgsConstructor() {
        Class<?> userClass = User.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(userClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasAllArgsConstructor() {
        Class<?> userClass = User.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(userClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 8);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    // <------------ ANNOTATION TESTS ------------> //

    //* [x] <-- Class: User --> //
    @Test
    public void class_isAnnotatedWithEntity() {
        assertNotNull(User.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    public void class_isAnnotatedWithTable() {
        assertNotNull(User.class.getDeclaredAnnotation(Table.class));
    }

    //* <-- Field: uuid --> //
    @Test
    public void uuidField_isAnnotatedWithId() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(Id.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void uuidField_isAnnotatedWithGeneratedValue() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(GeneratedValue.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void uuidFieldGeneratedValueAnnotation_isStrategyUUID() {
        try {
            assertEquals(GenerationType.UUID, User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(GeneratedValue.class)
                    .strategy()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void uuidField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void uuidFieldColumnAnnotation_isNameUuid() {
        try {
            assertEquals("uuid", User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(Column.class)
                    .name()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void uuidFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(Column.class)
                    .nullable());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void uuidFieldColumnAnnotation_isLength36() {
        try {
            assertEquals(36, User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(Column.class)
                    .length()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void uuidField_isAnnotatedWithJdbcTypeCode() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(JdbcTypeCode.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }
    @Test
    public void uuidFieldJdbcTypeCodeAnnotation_isSqlTypeVARCHAR() {
        try {
            assertEquals(SqlTypes.VARCHAR, User.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(JdbcTypeCode.class)
                    .value()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    //* [] <-- Field: username --> //
    @Test
    public void usernameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("username")
                    .getDeclaredAnnotation(NotBlank.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameField_isAnnotatedWithSize() {
        try {
            assertEquals(255, User.class.getDeclaredField("username").getDeclaredAnnotation(Size.class).max());

        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class
                    .getDeclaredField("username")
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameFieldColumnAnnotation_isNameUsername() {
        try {
            assertEquals("username", User.class.getDeclaredField("username").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void usernameFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("username").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void usernameFieldColumnAnnotation_isUniqueTrue() {
        try {
            assertTrue(User.class.getDeclaredField("username").getDeclaredAnnotation(Column.class).unique());
        } catch (NoSuchFieldException e) {fail();}
    }

    //* [] <-- Field: email --> //

    @Test
    public void emailField_isAnnotatedWithEmail() {
        try {
            assertNotNull(User.class.getDeclaredField("email").getDeclaredAnnotation(Email.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(User.class.getDeclaredField("email").getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailField_isAnnotatedWithSize() {
        try {
            assertEquals(255, User.class.getDeclaredField("email").getDeclaredAnnotation(Size.class).max());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class.getDeclaredField("email").getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isNameEmail() {
        try {
            assertEquals("email", User.class.getDeclaredField("email").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("email").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void emailFieldColumnAnnotation_isUniqueTrue() {
        try {
            assertTrue(User.class.getDeclaredField("email").getDeclaredAnnotation(Column.class).unique());
        } catch (NoSuchFieldException e) {fail();}
    }
    //* [] <-- Field: password --> //
    @Test
    public void passwordField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(User.class.getDeclaredField("password").getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void passwordField_isAnnotatedWithSize() {
        try {
            assertEquals(255, User.class.getDeclaredField("password").getDeclaredAnnotation(Size.class).max());
        } catch (NoSuchFieldException e) {
            fail();

        }}

    @Test
    public void passwordField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class.getDeclaredField("password").getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {
            fail();

        }}

    @Test
    public void passwordFieldColumnAnnotation_isNamePassword() {
        try {
            assertEquals("password", User.class.getDeclaredField("password").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();

        }}

    @Test
    public void passwordFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("password").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    //* [] <-- Field: createTime --> //

    @Test
    public void createTimeField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class.getDeclaredField("createTime").getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void createTimeFieldColumnAnnotation_isNameCreateTime() {
        try {
            assertEquals("create_time", User.class.getDeclaredField("createTime").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void createTimeFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("createTime").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void createTimeFieldColumnAnnotation_isInsertableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("createTime").getDeclaredAnnotation(Column.class).insertable());
        } catch (NoSuchFieldException e) {fail();}
    }

    //* [] <-- Field: updateTime --> //

    @Test
    public void updateTimeField_isAnnotatedWithColumn() {
        try {
            assertNotNull(User.class.getDeclaredField("updateTime").getDeclaredAnnotation(Column.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void updateTimeFieldColumnAnnotation_isNameUpdateTime() {
        try {
            assertEquals("update_time", User.class.getDeclaredField("updateTime").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void updateTimeFieldColumnAnnotation_isInsertableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("updateTime").getDeclaredAnnotation(Column.class).insertable());
        } catch (NoSuchFieldException e) {fail();}
    }

    //* [] <-- Field: roles --> //

    @Test
    public void roleField_isAnnotatedWithManyToOne() {
        try {
            assertNotNull(User.class.getDeclaredField("role").getDeclaredAnnotation(ManyToOne.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void roleFieldManyToOneAnnotation_hasFetchTypeEAGER() {
        try {
            assertEquals(FetchType.EAGER, User.class.getDeclaredField("role").getDeclaredAnnotation(ManyToOne.class).fetch());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void roleField_isAnnotatedWithJoinColumn() {
        try {
            assertNotNull(User.class.getDeclaredField("role").getDeclaredAnnotation(JoinColumn.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void roleFieldJoinColumnAnnotation_isNameRoleUuid() {
        try {
            assertEquals("role_uuid", User.class.getDeclaredField("role").getDeclaredAnnotation(JoinColumn.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void roleFieldJoinColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(User.class.getDeclaredField("role").getDeclaredAnnotation(JoinColumn.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }


    //* [] <-- Field: garments --> //

    @Test
    public void garmentsField_isAnnotatedWithOneToMany() {
        try {
            assertNotNull(User.class.getDeclaredField("garments").getDeclaredAnnotation(OneToMany.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void garmentsFieldOneToManyAnnotation_hasMappedByOwner() {
        try {
            assertEquals("owner", User.class.getDeclaredField("garments").getDeclaredAnnotation(OneToMany.class).mappedBy());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void garmentsField_isAnnotatedWithCascadeTypeALL() {
        try {
            assertTrue(Arrays.asList(User.class.getDeclaredField("garments").getDeclaredAnnotation(OneToMany.class).cascade()).contains(CascadeType.ALL));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void garmentsField_isAnnotatedWithOrphanRemovalTrue() {
        try {
            assertTrue(User.class.getDeclaredField("garments").getDeclaredAnnotation(OneToMany.class).orphanRemoval());
        } catch (NoSuchFieldException e) {fail();}
    }

    // [x] <------------ FIELD TESTS ------------> //

    //* [x] <-- Field: uuid --> //
    @Test
    public void uuidField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("uuid"));
    }

    @Test
    public void uuidField_shouldBePrivate() {
        try {
            Field uuidField = User.class.getDeclaredField("uuid");
            assertTrue(java.lang.reflect.Modifier.isPrivate(uuidField.getModifiers()), "uuid field should be private");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void uuidField_shouldBeOfTypeUUID() {
        try {
            Field uuidField = User.class.getDeclaredField("uuid");
            assertEquals(UUID.class, uuidField.getType(), "uuid field should be of type UUID");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }


    //* [x] <-- Field: username --> //
    @Test
    public void usernameField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("username"));
    }

    @Test
    public void usernameField_shouldBePrivate() {
        try {
            Field usernameField = User.class.getDeclaredField("username");
            assertTrue(java.lang.reflect.Modifier.isPrivate(usernameField.getModifiers()), "username field should be private");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void usernameField_shouldBeOfTypeString() {
        try {
            Field usernameField = User.class.getDeclaredField("username");
            assertEquals(String.class, usernameField.getType(), "username field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }


    //* [x] <-- Field: email --> //
    @Test
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("email"));
    }

    @Test
    public void emailField_shouldBePrivate() {
        try {
            Field emailField = User.class.getDeclaredField("email");
            assertTrue(java.lang.reflect.Modifier.isPrivate(emailField.getModifiers()), "email field should be private");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }

    @Test
    public void emailField_shouldBeOfTypeString() {
        try {
            Field emailField = User.class.getDeclaredField("email");
            assertEquals(String.class, emailField.getType(), "email field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The email field does not exist");
        }
    }


    //* [x] <-- Field: password --> //
    @Test
    public void passwordField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("password"));
    }

    @Test
    public void passwordField_shouldBePrivate() {
        try {
            Field passwordField = User.class.getDeclaredField("password");
            assertTrue(java.lang.reflect.Modifier.isPrivate(passwordField.getModifiers()), "password field should be private");
        } catch (NoSuchFieldException e) {
            fail("The password field does not exist");
        }
    }

    @Test
    public void passwordField_shouldBeOfTypeString() {
        try {
            Field passwordField = User.class.getDeclaredField("password");
            assertEquals(String.class, passwordField.getType(), "password field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The password field does not exist");
        }
    }

    //* [x] <-- Field: createTime --> //
    @Test
    public void createTimeField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("createTime"));
    }

    @Test
    public void createTimeField_shouldBePrivate() {
        try {
            Field createTimeField = User.class.getDeclaredField("createTime");
            assertTrue(java.lang.reflect.Modifier.isPrivate(createTimeField.getModifiers()), "createTime field should be private");
        } catch (NoSuchFieldException e) {
            fail("The createTime field does not exist");
        }
    }

    @Test
    public void createTimeField_shouldBeOfTypeInstant() {
        try {
            Field createTimeField = User.class.getDeclaredField("createTime");
            assertEquals(Instant.class, createTimeField.getType(), "createTime field should be of type Instant");
        } catch (NoSuchFieldException e) {
            fail("The createTime field does not exist");
        }
    }

    //* [x] <-- Field: updateTime --> //
    @Test
    public void updateTimeField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("updateTime"));
    }

    @Test
    public void updateTimeField_shouldBePrivate() {
        try {
            Field updateTimeField = User.class.getDeclaredField("updateTime");
            assertTrue(java.lang.reflect.Modifier.isPrivate(updateTimeField.getModifiers()), "updateTime field should be private");
        } catch (NoSuchFieldException e) {
            fail("The updateTime field does not exist");
        }
    }

    @Test
    public void updateTimeField_shouldBeOfTypeInstant() {
        try {
            Field updateTimeField = User.class.getDeclaredField("updateTime");
            assertEquals(Instant.class, updateTimeField.getType(), "updateTime field should be of type Instant");
        } catch (NoSuchFieldException e) {
            fail("The updateTime field does not exist");
        }
    }

    //* [x] <-- Field: role --> //
    @Test
    public void roleField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("role"));
    }

    @Test
    public void roleField_shouldBePrivate() {
        try {
            Field roleField = User.class.getDeclaredField("role");
            assertTrue(java.lang.reflect.Modifier.isPrivate(roleField.getModifiers()), "role field should be private");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }

    @Test
    public void rolesField_shouldBeOfTypeRole() {
        try {
            Field roleField = User.class.getDeclaredField("role");
            assertEquals(Role.class, roleField.getType(), "role field should be of type Role");
        } catch (NoSuchFieldException e) {
            fail("The role field does not exist");
        }
    }


    //* <-- Field: garments --> //
    @Test
    public void garmentsField_doesExist() {
        assertDoesNotThrow(() -> User.class.getDeclaredField("garments"));
    }

    @Test
    public void garmentsField_shouldBePrivate() {
        try {
            Field garmentsField = User.class.getDeclaredField("garments");
            assertTrue(java.lang.reflect.Modifier.isPrivate(garmentsField.getModifiers()), "garments field should be private");
        } catch (NoSuchFieldException e) {
            fail("The garments field does not exist");
        }
    }

    @Test
    public void garmentsField_shouldBeOfTypeSet() {
        try {
            Field garmentsField = User.class.getDeclaredField("garments");
            assertEquals(Set.class, garmentsField.getType(), "garments field should be of type Set");
        } catch (NoSuchFieldException e) {
            fail("The garments field does not exist");
        }
    }

    //: <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUuid", "getEmail", "getUsername", "getPassword", "getCreateTime", "getUpdateTime", "getRole", "getGarments"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> User.class.getDeclaredMethod(getterName));
    }

    //: <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUuid, java.util.UUID", "setUsername, java.lang.String", "setEmail, java.lang.String", "setPassword, java.lang.String", "setCreateTime, java.time.Instant", "setUpdateTime, java.time.Instant", "setRole, com.fitcom.fitcom_restapi.model.Role", "setGarments, java.util.Set"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> User.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // [ ] <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> User.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyUuid() {
        User user1 = DataUtil.getTestUser(0);
        User user2 = DataUtil.getTestUser(0);

        user1.setEmail("NotSameEmail@foo.bar");
        user1.setUsername("NotSameUsername");
        user1.setPassword("N0tS@mePassword");

        assertEquals(user1, user2);
    }


    // [ ] <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> User.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesUuid() {
        User user1 = DataUtil.getTestUser(0);
        User user2 = DataUtil.getTestUser(0);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

}