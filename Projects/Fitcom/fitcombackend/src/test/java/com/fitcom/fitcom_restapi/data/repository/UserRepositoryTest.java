package com.fitcom.fitcom_restapi.data.repository;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.repository.UserRepository;
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

public class UserRepositoryTest {

    @Test
    public void repository_isAnnotatedWithRepository() {
        assertNotNull(UserRepository.class.getDeclaredAnnotation(Repository.class));
    }
    @Test
    public void repository_isInterface() {
        assertTrue(UserRepository.class.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(UserRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isUser() {
        assertTrue(Arrays.stream(UserRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(User.class)));
    }

    @Test
    public void repositoryId_isUUID() {
        assertTrue(Arrays.stream(UserRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(UUID.class)));
    }

    //: Find

    //: ByUUID
    @Test
    public void repositoryFindByUUIDForUpdate_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class));
    }

    @Test
    public void findByUUIDForUpdateMethod_hasLockAnnotation() {
        try {
            Method method = UserRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            assertNotNull(method.getAnnotation(Lock.class));
            assertEquals(LockModeType.PESSIMISTIC_WRITE, method.getAnnotation(Lock.class).value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void findByUUIDForUpdateMethod_hasQueryHintAnnotation() {
        try {
            Method method = UserRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class);
            QueryHints hints = method.getAnnotation(QueryHints.class);
            assertNotNull(hints);
            assertTrue(hints.value().length > 0);
            assertEquals("0", hints.value()[0].value());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUIDForUpdate_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, UserRepository.class.getDeclaredMethod("findByUUIDForUpdate", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByUUID_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("findByUUID", UUID.class));
    }

    @Test
    public void repositoryFindByUUID_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, UserRepository.class.getDeclaredMethod("findByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByUsername
    @Test
    public void repositoryFindByUsername_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("findByUsername", String.class));
    }

    @Test
    public void repositoryFindByUsername_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, UserRepository.class.getDeclaredMethod("findByUsername", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByEmail
    @Test
    public void repositoryFindByEmail_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("findByEmail", String.class));
    }

    @Test
    public void repositoryFindByEmail_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, UserRepository.class.getDeclaredMethod("findByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByPrincipal
    @Test
    public void repositoryFindByPrincipal_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("findByPrincipal", String.class));
    }

    @Test
    public void repositoryFindByPrincipal_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, UserRepository.class.getDeclaredMethod("findByPrincipal", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: Delete

    //: ByUUID
    @Test
    public void repositoryDeleteByUUID_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("deleteByUUID", UUID.class));
    }

    @Test
    public void deleteByUUIDMethod_hasModifyingAnnotation() {
        try {
            Method method = UserRepository.class.getDeclaredMethod("deleteByUUID", UUID.class);
            assertNotNull(method.getAnnotation(Modifying.class));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryDeleteByUUID_isVoid() {
        try {
            assertEquals(void.class, UserRepository.class.getDeclaredMethod("deleteByUUID", UUID.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: Exists

    //: ByUsername
    @Test
    public void repositoryExistsByUsername_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("existsByUsername", String.class));
    }

    @Test
    public void repositoryExistsByUsername_returnsBoolean() {
        try {
            assertEquals(Boolean.class, UserRepository.class.getDeclaredMethod("existsByUsername", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByEmail
    @Test
    public void repositoryExistsByEmail_exists() {
        assertDoesNotThrow(() -> UserRepository.class.getDeclaredMethod("existsByEmail", String.class));
    }

    @Test
    public void repositoryExistsByEmail_returnsBoolean() {
        try {
            assertEquals(Boolean.class, UserRepository.class.getDeclaredMethod("existsByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

}
