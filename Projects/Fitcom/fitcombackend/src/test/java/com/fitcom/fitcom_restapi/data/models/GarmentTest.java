package com.fitcom.fitcom_restapi.data.models;

import com.fitcom.fitcom_restapi.model.ECategory;
import com.fitcom.fitcom_restapi.model.Garment;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.util.DataUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GarmentTest {

    // [x] <------------ CONSTRUCTOR TESTS ------------> //

    @Test
    public void constructor_hasNoArgsConstructor() {
        Class<?> garmentClass = Garment.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(garmentClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    @Test
    public void constructor_hasAllArgsConstructor() {
        Class<?> garmentClass = Garment.class;
        boolean doesUserClassHaveEmptyConstructor = Arrays.stream(garmentClass.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 5);

        assertTrue(doesUserClassHaveEmptyConstructor);
    }

    // <------------ ANNOTATION TESTS ------------> //

    //* [x] <-- Class: Garment --> //
    @Test
    public void class_isAnnotatedWithEntity() {
        assertNotNull(Garment.class.getDeclaredAnnotation(Entity.class));
    }

    @Test
    public void class_isAnnotatedWithTable() {
        assertNotNull(Garment.class.getDeclaredAnnotation(Table.class));
    }

    //* <-- Field: uuid --> //
    @Test
    public void uuidField_isAnnotatedWithId() {
        try {
            assertNotNull(Garment.class
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
            assertNotNull(Garment.class
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
            assertEquals(GenerationType.UUID, Garment.class
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
            assertNotNull(Garment.class
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
            assertEquals("uuid", Garment.class
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
            assertFalse(Garment.class
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
            assertEquals(36, Garment.class
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
            assertNotNull(Garment.class
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
            assertEquals(SqlTypes.VARCHAR, Garment.class
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
    public void nameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(Garment.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(NotBlank.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameField_isAnnotatedWithSize() {
        try {
            assertEquals(255, Garment.class.getDeclaredField("name").getDeclaredAnnotation(Size.class).max());

        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Garment.class
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
            assertEquals("name", Garment.class.getDeclaredField("name").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void nameFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(Garment.class.getDeclaredField("name").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void nameFieldColumnAnnotation_isUniqueTrue() {
        try {
            assertTrue(Garment.class.getDeclaredField("name").getDeclaredAnnotation(Column.class).unique());
        } catch (NoSuchFieldException e) {fail();}
    }

    // <-- Field: category --> //
    @Test
    public void categoryField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Garment.class
                    .getDeclaredField("category")
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void categoryFieldColumnAnnotation_isNameCategory() {
        try {
            assertEquals("category", Garment.class
                    .getDeclaredField("category")
                    .getDeclaredAnnotation(Column.class)
                    .name()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    public void categoryFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(Garment.class
                    .getDeclaredField("category")
                    .getDeclaredAnnotation(Column.class)
                    .nullable());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void categoryField_isAnnotatedWithEnumerated() {
        try {
            assertNotNull(Garment.class
                    .getDeclaredField("category")
                    .getDeclaredAnnotation(Enumerated.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }
    @Test
    public void categoryFieldEnumeratedAnnotation_isEnumTypeSTRING() {
        try {
            assertEquals(EnumType.STRING, Garment.class
                    .getDeclaredField("category")
                    .getDeclaredAnnotation(Enumerated.class)
                    .value()
            );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            fail();
        }
    }

    // <-- Field: color --> //
    @Test
    public void colorField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(Garment.class
                    .getDeclaredField("color")
                    .getDeclaredAnnotation(NotBlank.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void colorField_isAnnotatedWithSize() {
        try {
            assertEquals(255, Garment.class.getDeclaredField("color").getDeclaredAnnotation(Size.class).max());

        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void colorField_isAnnotatedWithColumn() {
        try {
            assertNotNull(Garment.class
                    .getDeclaredField("color")
                    .getDeclaredAnnotation(Column.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void colorFieldColumnAnnotation_isNameColor() {
        try {
            assertEquals("color", Garment.class.getDeclaredField("color").getDeclaredAnnotation(Column.class).name());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void colorFieldColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(Garment.class.getDeclaredField("color").getDeclaredAnnotation(Column.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    //* [] <-- Field: owner --> //

    @Test
    public void ownerField_isAnnotatedWithManyToOne() {
        try {
            assertNotNull(Garment.class.getDeclaredField("owner").getDeclaredAnnotation(ManyToOne.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void ownerFieldManyToOneAnnotation_hasFetchTypeLAZY() {
        try {
            assertEquals(FetchType.LAZY, Garment.class.getDeclaredField("owner").getDeclaredAnnotation(ManyToOne.class).fetch());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void ownerField_isAnnotatedWithJoinColumn() {
        try {
            assertNotNull(Garment.class.getDeclaredField("owner").getDeclaredAnnotation(JoinColumn.class));
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void ownerFieldJoinColumnAnnotation_isNameRoleUuid() {
        try {
            assertEquals("owner_uuid", Garment.class.getDeclaredField("owner").getDeclaredAnnotation(JoinColumn.class).name());
        } catch (NoSuchFieldException e) {fail();}
    }

    @Test
    public void ownerFieldJoinColumnAnnotation_isNullableFalse() {
        try {
            assertFalse(Garment.class.getDeclaredField("owner").getDeclaredAnnotation(JoinColumn.class).nullable());
        } catch (NoSuchFieldException e) {fail();}
    }

    // [x] <------------ FIELD TESTS ------------> //

    //* [x] <-- Field: uuid --> //
    @Test
    public void uuidField_doesExist() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredField("uuid"));
    }

    @Test
    public void uuidField_shouldBePrivate() {
        try {
            Field uuidField = Garment.class.getDeclaredField("uuid");
            assertTrue(java.lang.reflect.Modifier.isPrivate(uuidField.getModifiers()), "uuid field should be private");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }

    @Test
    public void uuidField_shouldBeOfTypeUUID() {
        try {
            Field uuidField = Garment.class.getDeclaredField("uuid");
            assertEquals(UUID.class, uuidField.getType(), "uuid field should be of type UUID");
        } catch (NoSuchFieldException e) {
            fail("The uuid field does not exist");
        }
    }


    //* [x] <-- Field: name --> //
    @Test
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredField("name"));
    }

    @Test
    public void nameField_shouldBePrivate() {
        try {
            Field nameField = Garment.class.getDeclaredField("name");
            assertTrue(java.lang.reflect.Modifier.isPrivate(nameField.getModifiers()), "name field should be private");
        } catch (NoSuchFieldException e) {
            fail("The name field does not exist");
        }
    }

    @Test
    public void nameField_shouldBeOfTypeString() {
        try {
            Field nameField = Garment.class.getDeclaredField("name");
            assertEquals(String.class, nameField.getType(), "name field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The name field does not exist");
        }
    }

    // <-- Field: category -->
    @Test
    public void categoryField_doesExist() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredField("category"));
    }

    @Test
    public void categoryField_shouldBePrivate() {
        try {
            Field categoryField = Garment.class.getDeclaredField("category");
            assertTrue(java.lang.reflect.Modifier.isPrivate(categoryField.getModifiers()), "category field should be private");
        } catch (NoSuchFieldException e) {
            fail("The category field does not exist");
        }
    }

    @Test
    public void categoryField_shouldBeOfTypeECategory() {
        try {
            Field categoryField = Garment.class.getDeclaredField("category");
            assertEquals(ECategory.class, categoryField.getType(), "category field should be of type ECategory");
        } catch (NoSuchFieldException e) {
            fail("The category field does not exist");
        }
    }

    // <-- Field: color-->
    @Test
    public void colorField_doesExist() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredField("color"));
    }

    @Test
    public void colorField_shouldBePrivate() {
        try {
            Field colorField = Garment.class.getDeclaredField("color");
            assertTrue(java.lang.reflect.Modifier.isPrivate(colorField.getModifiers()), "color field should be private");
        } catch (NoSuchFieldException e) {
            fail("The color field does not exist");
        }
    }

    @Test
    public void colorField_shouldBeOfTypeString() {
        try {
            Field colorField = Garment.class.getDeclaredField("color");
            assertEquals(String.class, colorField.getType(), "color field should be of type String");
        } catch (NoSuchFieldException e) {
            fail("The color field does not exist");
        }
    }

    //* [x] <-- Field: owner --> //
    @Test
    public void ownerField_doesExist() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredField("owner"));
    }

    @Test
    public void ownerField_shouldBePrivate() {
        try {
            Field ownerField = Garment.class.getDeclaredField("owner");
            assertTrue(java.lang.reflect.Modifier.isPrivate(ownerField.getModifiers()), "owner field should be private");
        } catch (NoSuchFieldException e) {
            fail("The owner field does not exist");
        }
    }

    @Test
    public void ownerField_shouldBeOfTypeUser() {
        try {
            Field ownerField = Garment.class.getDeclaredField("owner");
            assertEquals(User.class, ownerField.getType(), "owner field should be of type User");
        } catch (NoSuchFieldException e) {
            fail("The owner field does not exist");
        }
    }

    // [x] <------------ GETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"getUuid", "getName", "getCategory", "getColor", "getOwner"})
    public void checkGetter_doExist(String getterName) {
        assertDoesNotThrow(() -> Garment.class.getDeclaredMethod(getterName));
    }

    // [x] <------------ SETTER TESTS ------------> //
    @ParameterizedTest
    @CsvSource(value = {"setUuid, java.util.UUID", "setName, java.lang.String", "setCategory, com.fitcom.fitcom_restapi.model.ECategory", "setColor, java.lang.String", "setOwner, com.fitcom.fitcom_restapi.model.User"})
    public void checkSetter_doExist(String setterName, String parameterClassName) {
        assertDoesNotThrow(() -> Garment.class.getDeclaredMethod(setterName, Class.forName(parameterClassName)));
    }

    // [ ] <------------ EQUALS TESTS ------------> //
    @Test
    public void equalsMethod_isDeclared() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredMethod("equals", Object.class));
    }

    @Test
    public void equalsMethod_comparesOnlyUuid() {
        Garment garment1 = DataUtil.getTestGarment(0);
        Garment garment2 = DataUtil.getTestGarment(0);

        garment1.setName("NotSameName");

        assertEquals(garment1, garment2);
    }


    // [ ] <------------ HASHCODE TESTS ------------> //
    @Test
    public void hashCodeMethod_isDeclared() {
        assertDoesNotThrow(() -> Garment.class.getDeclaredMethod("hashCode"));
    }

    @Test
    public void hashCodeMethod_hashesUuid() {
        Garment garment1 = DataUtil.getTestGarment(0);
        Garment garment2 = DataUtil.getTestGarment(0);

        assertEquals(garment1.hashCode(), garment2.hashCode());
    }

}