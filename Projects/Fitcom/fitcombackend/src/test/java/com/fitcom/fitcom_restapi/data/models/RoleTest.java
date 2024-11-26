package com.fitcom.fitcom_restapi.data.models;

import com.fitcom.fitcom_restapi.model.ERole;
import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.util.DataUtil;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    // [x] <------------ CONSTRUCTOR TESTS ------------> //

    @Test
    public void constructor_hasNoArgsConstructor() {
        Class<?> roleClass = Role.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(roleClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasAllArgsConstructor() {
        Class<?> roleClass = Role.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(roleClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 3);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasConstructorWithoutUsers() {
        Class<?> roleClass = Role.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(roleClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 2);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    // <------------ ANNOTATION TESTS ------------> //

    //* [x] <-- Class: Role --> //
    @Test
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Role.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    public void class_isAnnotatedWithTable() {
        assertNotNull(Role.class.getDeclaredAnnotation(Table.class));
    }

    //* <-- Field: uuid --> //
    @Test
    public void uuidField_isAnnotatedWithId() {
        try {
            assertNotNull(Role.class
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
            assertNotNull(Role.class
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
            assertEquals(GenerationType.UUID, Role.class
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
            assertNotNull(Role.class
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
            assertEquals("uuid", Role.class
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
            assertFalse(Role.class
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
            assertEquals(36, Role.class
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
            assertNotNull(Role.class
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
            assertEquals(SqlTypes.VARCHAR, Role.class
                    .getDeclaredField("uuid")
                    .getDeclaredAnnotation(JdbcTypeCode.class)
                    .value()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    //* [] <-- Field: name --> //
    @Test
    public void nameField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameFieldColumnAnnotation_isNameName() {
        try {
            assertEquals("name", Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class)
                    .name()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void nameFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Column.class)
                    .nullable());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void nameField_isAnnotatedWithEnumerated() {
        try {
            assertNotNull(Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Enumerated.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }
    @Test
    public void nameFieldEnumeratedAnnotation_isEnumTypeSTRING() {
        try {
            assertEquals(EnumType.STRING, Role.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(Enumerated.class)
                    .value()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

   //* [] <-- Field: users --> //

    @Test
    public void usersField_isAnnotatedWithOneToMany() {
        try {
            assertNotNull(Role.class.getDeclaredField("users").getDeclaredAnnotation(OneToMany.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void usersFieldOneToManyAnnotation_hasMappedByRole() {
        try {
            assertEquals("role", Role.class.getDeclaredField("users").getDeclaredAnnotation(OneToMany.class).mappedBy());
        } catch (NoSuchFieldException e) {fail();}
    }

    // [x] <------------ FIELD TESTS ------------> //
    //* [x] <-- Field: uuid --> //
    @Test
    public void uuidField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("uuid"));
    }

    @Test
    public void uuidField_shouldBePrivate() {
        try {
            Field uuidField = Role.class.getDeclaredField("uuid");
            assertTrue(java.lang.reflect.Modifier.isPrivate(uuidField.getModifiers()), "uuid field should be private");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void uuidField_shouldBeOfTypeUUID() {
        try {
            Field uuidField = Role.class.getDeclaredField("uuid");
            assertEquals(UUID.class, uuidField.getType(), "uuid field should be of type UUID");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    //* [x] <-- Field: name --> //
    @Test
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("name"));
    }

    @Test
    public void nameField_shouldBePrivate() {
        try {
            Field usernameField = Role.class.getDeclaredField("name");
            assertTrue(java.lang.reflect.Modifier.isPrivate(usernameField.getModifiers()), "username field should be private");
        } catch (NoSuchFieldException e) {
            fail("The username field does not exist");
        }
    }

    @Test
    public void nameField_shouldBeOfTypeERole() {
        try {
            Field nameField = Role.class.getDeclaredField("name");
            assertEquals(ERole.class, nameField.getType(), "name field should be of type ERole");
        } catch (NoSuchFieldException e) {
            fail("The name field does not exist");
        }
    }

    //* [x] <-- Field: users --> //
    @Test
    public void usersField_doesExist() {
        assertDoesNotThrow(() -> Role.class.getDeclaredField("users"));
    }

    @Test
    public void usersField_shouldBePrivate() {
        try {
            Field usersField = Role.class.getDeclaredField("users");
            assertTrue(java.lang.reflect.Modifier.isPrivate(usersField.getModifiers()), "garments field should be private");
        } catch (NoSuchFieldException e) {
            fail("The users field does not exist");
        }
    }

    @Test
    public void usersField_shouldBeOfTypeSet() {
        try {
            Field usersField = Role.class.getDeclaredField("users");
            assertEquals(Set.class, usersField.getType(), "users field should be of type Set");
        } catch (NoSuchFieldException e) {
            fail("The users field does not exist");
        }
    }

    // [x] <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUuid", "getName", "getUsers"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod(getterName));
    }

    // [x] <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUuid, java.util.UUID", "setName, com.fitcom.fitcom_restapi.model.ERole", "setUsers, java.util.Set"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // [ ] <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyUuid() {
        Role role1 = DataUtil.getTestRole(0);
        Role role2 = DataUtil.getTestRole(0);

        role1.setName(ERole.ROLE_ADMIN);
        assertEquals(role1, role2);
    }


    // [ ] <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Role.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesUuid() {
        Role role1 = DataUtil.getTestRole(0);
        Role role2 = DataUtil.getTestRole(0);

        assertEquals(role1.hashCode(), role2.hashCode());
    }

}