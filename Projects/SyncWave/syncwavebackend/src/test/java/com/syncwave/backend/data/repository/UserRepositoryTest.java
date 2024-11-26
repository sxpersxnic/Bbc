package com.syncwave.backend.data.repository;

import com.syncwave.backend.model.User;
import com.syncwave.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {
    private final Class<UserRepository> clazz = UserRepository.class;

    @Test
    public void repository_isAnnotatedWithRepository() {
        assertNotNull(clazz.getDeclaredAnnotation(Repository.class));
    }
    @Test
    public void repository_isInterface() {
        assertTrue(clazz.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(clazz.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isUser() {
        assertTrue(Arrays.stream(clazz.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(User.class)));
    }
    @Test
    public void repositoryId_isLong() {
        assertTrue(Arrays.stream(clazz.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Long.class)));
    }

    @Test
    public void repositoryFindByUsername_exists() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("findByUsername", String.class));
    }

    @Test
    public void repositoryFindByUsername_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, clazz.getDeclaredMethod("findByUsername", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindByEmail_exists() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("findByEmail", String.class));
    }

    @Test
    public void repositoryFindByEmail_returnsOptionalUser() {
        try {
            assertEquals(Optional.class, clazz.getDeclaredMethod("findByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryExistsByUsername_exists() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("existsByUsername", String.class));
    }

    @Test
    public void repositoryExistsByUsername_returnsBoolean() {
        try {
            assertEquals(boolean.class,
                    clazz.getDeclaredMethod("existsByUsername", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    //: ByEmail
    @Test
    public void repositoryExistsByEmail_exists() {
        assertDoesNotThrow(() -> clazz.getDeclaredMethod("existsByEmail", String.class));
    }

    @Test
    public void repositoryExistsByEmail_returnsBoolean() {
        try {
            assertEquals(boolean.class, clazz.getDeclaredMethod("existsByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }
}