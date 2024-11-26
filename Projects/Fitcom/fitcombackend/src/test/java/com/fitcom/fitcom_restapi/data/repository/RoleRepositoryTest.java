package com.fitcom.fitcom_restapi.data.repository;

import com.fitcom.fitcom_restapi.model.ERole;
import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.repository.RoleRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RoleRepositoryTest {
    @Test
    public void repository_isAnnotatedWithRepository() {
        assertNotNull(RoleRepository.class.getDeclaredAnnotation(Repository.class));
    }
    @Test
    public void repository_isInterface() {
        assertTrue(RoleRepository.class.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(RoleRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isRole() {
        assertTrue(Arrays.stream(RoleRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Role.class)));
    }

    @Test
    public void repositoryId_isUUID() {
        assertTrue(Arrays.stream(RoleRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(UUID.class)));
    }

    //: Find

    //: ByUUID
    @Test
    public void repositoryFindByUUIDForUpdate_exists() {
        assertDoesNotThrow(() -> RoleRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class));
    }

    @Test
    public void findByUUIDForUpdateMethod_hasLockAnnotation() {
        try {
            Method method = RoleRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            assertNotNull(method.getAnnotation(Lock.class));
            assertEquals(LockModeType.PESSIMISTIC_WRITE, method.getAnnotation(Lock.class).value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void findByUUIDForUpdateMethod_hasQueryHintAnnotation() {
        try {
            Method method = RoleRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            QueryHints hints = method.getAnnotation(QueryHints.class);
            assertNotNull(hints);
            assertTrue(hints.value().length > 0);
            assertEquals("0", hints.value()[0].value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUIDForUpdate_returnsOptionalRole() {
        try {
            assertEquals(Optional.class, RoleRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUID_exists() {
        assertDoesNotThrow(() -> RoleRepository.class.getDeclaredMethod("findByUUID", UUID.class));
    }

    @Test
    public void repositoryFindByUUID_returnsOptionalRole() {
        try {
            assertEquals(Optional.class, RoleRepository.class.getDeclaredMethod("findByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByName
    @Test
    public void repositoryFindByName_exists() {
        assertDoesNotThrow(() -> RoleRepository.class.getDeclaredMethod("findByName", ERole.class));
    }

    //: Delete ByUUID
    @Test
    public void repositoryDeleteByUUID_exists() {
        assertDoesNotThrow(() -> RoleRepository.class.getDeclaredMethod("deleteByUUID", UUID.class));
    }

    @Test
    public void deleteByUUIDMethod_hasModifyingAnnotation() {
        try {
            Method method = RoleRepository.class.getDeclaredMethod("deleteByUUID", UUID.class);
            assertNotNull(method.getAnnotation(Modifying.class));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryDeleteByUUID_isVoid() {
        try {
            assertEquals(void.class, RoleRepository.class.getDeclaredMethod("deleteByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: Exists

    //: ByName
    @Test
    public void repositoryExistsByName_exists() {
        assertDoesNotThrow(() -> RoleRepository.class.getDeclaredMethod("existsByName", ERole.class));
    }

    @Test
    public void repositoryExistsByName_returnsBoolean() {
        try {
            assertEquals(Boolean.class, RoleRepository.class.getDeclaredMethod("existsByName", ERole.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }
}
