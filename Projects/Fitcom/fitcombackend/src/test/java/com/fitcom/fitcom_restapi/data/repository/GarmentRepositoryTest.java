package com.fitcom.fitcom_restapi.data.repository;

import com.fitcom.fitcom_restapi.model.Garment;
import com.fitcom.fitcom_restapi.repository.GarmentRepository;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GarmentRepositoryTest {
    @Test
    public void repository_isAnnotatedWithRepository() {
        assertNotNull(GarmentRepository.class.getDeclaredAnnotation(Repository.class));
    }
    @Test
    public void repository_isInterface() {
        assertTrue(GarmentRepository.class.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(GarmentRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isGarment() {
        assertTrue(Arrays.stream(GarmentRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Garment.class)));
    }

    @Test
    public void repositoryId_isUUID() {
        assertTrue(Arrays.stream(GarmentRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(UUID.class)));
    }

    //: Find

    //: ByUUID
    @Test
    public void repositoryFindByUUIDForUpdate_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class));
    }

    @Test
    public void findByUUIDForUpdateMethod_hasLockAnnotation() {
        try {
            Method method = GarmentRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            assertNotNull(method.getAnnotation(Lock.class));
            assertEquals(LockModeType.PESSIMISTIC_WRITE, method.getAnnotation(Lock.class).value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void findByUUIDForUpdateMethod_hasQueryHintAnnotation() {
        try {
            Method method = GarmentRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            QueryHints hints = method.getAnnotation(QueryHints.class);
            assertNotNull(hints);
            assertTrue(hints.value().length > 0);
            assertEquals("0", hints.value()[0].value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUIDForUpdate_returnsOptionalGarment() {
        try {
            assertEquals(Optional.class, GarmentRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUID_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("findByUUID", UUID.class));
    }

    @Test
    public void repositoryFindByUUID_returnsOptionalGarment() {
        try {
            assertEquals(Optional.class, GarmentRepository.class.getDeclaredMethod("findByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindOwnerByUUIDForMapping_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("findOwnerByUUIDForMapping", UUID.class));
    }

    @Test
    public void repositoryFindOwnerByUUIDForMapping_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, GarmentRepository.class.getDeclaredMethod("findOwnerByUUIDForMapping", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindOwnedGarmentsByUUID_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("findOwnedGarmentsByUUID", UUID.class));
    }

    @Test
    public void repositoryFindOwnedGarmentsByUUID_returnsListGarment() {
        try {
            assertEquals(List.class, GarmentRepository.class.getDeclaredMethod("findOwnedGarmentsByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByName
    @Test
    public void repositoryFindByName_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("findByName", String.class));
    }

    @Test
    public void repositoryFindByName_returnsOptionalGarment() {
        try {
            assertEquals(Optional.class, GarmentRepository.class.getDeclaredMethod("findByName", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: Delete ByUUID
    @Test
    public void repositoryDeleteByUUID_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("deleteByUUID", UUID.class));
    }

    @Test
    public void deleteByUUIDMethod_hasModifyingAnnotation() {
        try {
            Method method = GarmentRepository.class.getDeclaredMethod("deleteByUUID", UUID.class);
            assertNotNull(method.getAnnotation(Modifying.class));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryDeleteByUUID_isVoid() {
        try {
            assertEquals(void.class, GarmentRepository.class.getDeclaredMethod("deleteByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: Exists

    //: ByName
    @Test
    public void repositoryExistsByName_exists() {
        assertDoesNotThrow(() -> GarmentRepository.class.getDeclaredMethod("existsByName", String.class));
    }

    @Test
    public void repositoryExistsByName_returnsBoolean() {
        try {
            assertEquals(Boolean.class, GarmentRepository.class.getDeclaredMethod("existsByName", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }
}
