package com.fitcom.fitcom_restapi.data.service;

import com.fitcom.fitcom_restapi.exceptions.FailedValidationException;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.repository.RoleRepository;
import com.fitcom.fitcom_restapi.repository.UserRepository;
import com.fitcom.fitcom_restapi.service.UserService;
import com.fitcom.fitcom_restapi.util.DataUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    //TODO: @Mock
    //TODO: private PasswordEncoder passwordEncoder;


    @Test
    public void checkFindAll_whenUsersExist_thenUsersAreReturned() {
        List<User> expectedUsers = DataUtil.getTestUsers();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll();

        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            User expectedUser = expectedUsers.get(i);
            User actualUser = actualUsers.get(i);

            assertEquals(expectedUser.getUuid(), actualUser.getUuid());
            assertEquals(expectedUser.getUsername(), actualUser.getUsername());
            assertEquals(expectedUser.getEmail(), actualUser.getEmail());
            assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        }
    }

    @Test
    public void checkFindByUUID_whenExistingUUID_thenUserIsReturned() {
        User expectedUser = DataUtil.getTestUser(0);
        UUID uuid = expectedUser.getUuid();
        when(userRepository.findByUUID(eq(uuid))).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findByUUID(uuid);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUuid(), actualUser.getUuid());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void checkFindByUUID_whenNonExistingUUID_thenEntityNotFoundExceptionIsThrown() {
        UUID uuid = UUID.randomUUID();
        when(userRepository.findById(eq(uuid))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByUUID(uuid));
    }

    @Test
    public void checkFindByEmail_whenExistingEmail_thenUserIsReturned() {
        User expectedUser = DataUtil.getTestUser(0);
        String email = expectedUser.getEmail();
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findByEmail(email);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUuid(), actualUser.getUuid());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void checkFindByEmail_whenNonExistingEmail_thenEntityNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail("test@test.com"));
    }

    @Test
    public void checkUpdate_whenValidUser_thenChangedUserIsReturned() {
        String newEmail = "test@test.com";

        User unchangedUser = DataUtil.getTestUser(0);
        UUID uuid = unchangedUser.getUuid();
        when(userRepository.findByUUIDForUpdate(eq(uuid))).thenReturn(Optional.of(unchangedUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User changedUser = DataUtil.getTestUser(0);
        changedUser.setEmail(newEmail);
        User updatedUser = userService.update(changedUser, changedUser.getUuid());

        assertEquals(updatedUser.getEmail(), newEmail);
    }

    @Test
    public void checkUpdate_whenBlankEmail_thenFailedValidationExceptionIsThrown() {
        User unchangedUser = DataUtil.getTestUser(0);
        UUID uuid = unchangedUser.getUuid();
        when(userRepository.findByUUIDForUpdate(eq(uuid))).thenReturn(Optional.of(unchangedUser));

        User changedUser = DataUtil.getTestUser(0);
        changedUser.setEmail("");

        assertThrows(FailedValidationException.class, () -> userService.update(changedUser, uuid));
    }

    @Test
    public void checkUpdate_whenNonExistingUUID_thenEntityNotFoundExceptionIsThrown() {
        User user = DataUtil.getTestUser(0);
        UUID uuid = user.getUuid();
        when(userRepository.findById(eq(uuid))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(user, uuid));
    }

    @Test
    public void checkUpdate_whenEmailAlreadyExists_thenThrowConstraintViolation() {
        User user = DataUtil.getTestUser(0);
        UUID uuid = user.getUuid();
        when(userRepository.save(eq(user))).thenThrow(ConstraintViolationException.class);
        when(userRepository.findByUUIDForUpdate(eq(uuid))).thenReturn(Optional.of(user));
        assertThrows(ConstraintViolationException.class, () -> userService.update(user, uuid));
    }

    @Test
    public void checkDeleteByUUID_whenNonExistingUUID_thenEntityNotFoundExceptionIsThrown() {
        UUID uuid = UUID.randomUUID();
        doThrow(new EntityNotFoundException()).when(userRepository).deleteByUUID(uuid);
        assertThrows(EntityNotFoundException.class, () -> userService.deleteByUUID(uuid));
    }
}