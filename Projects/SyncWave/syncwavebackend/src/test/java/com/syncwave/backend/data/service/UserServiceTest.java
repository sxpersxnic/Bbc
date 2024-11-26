package com.syncwave.backend.data.service;


import com.syncwave.backend.lib.exceptions.FailedValidationException;
import com.syncwave.backend.model.User;
import com.syncwave.backend.repository.UserRepository;
import com.syncwave.backend.service.UserService;
import com.syncwave.backend.util.DataUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    private List<User> userList;
    private User user1;
    private User user2;
    private User invalidUser;
    private final Class<UserService> clazz = UserService.class;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
        List<User> userList = DataUtil.getTestUsers();
        User user1 = DataUtil.getTestUser(0);
        User user2 = DataUtil.getTestUser(0);
        User invalidUser = DataUtil.getTestUser(1);
    }

    @Test
    public void checkFindAll_whenUsersExist_thenUsersAreReturned() {
        List<User> expectedUsers = DataUtil.getTestUsers();
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll();

        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            User expectedUser = expectedUsers.get(i);
            User actualUser = actualUsers.get(i);

            assertEquals(expectedUser.getId(), actualUser.getId());
            assertEquals(expectedUser.getUsername(), actualUser.getUsername());
            assertEquals(expectedUser.getEmail(), actualUser.getEmail());
            assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        }
    }

    @Test
    public void checkFindById_whenExistingId_thenUserIsReturned() {
        User expectedUser = DataUtil.getTestUser(0);
        Long id = expectedUser.getId();
        when(userRepository.findById(eq(id))).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findById(id);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    public void checkFindById_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        when(userRepository.findById(eq(0L))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(0L));
    }

    @Test
    public void checkFindByEmail_whenExistingEmail_thenUserIsReturned() {
        User expectedUser = DataUtil.getTestUser(0);
        String email = expectedUser.getEmail();
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(expectedUser));

        User actualUser = userService.findByEmail(email);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
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
        Long id = unchangedUser.getId();
        when(userRepository.findByIdForUpdate(eq(id))).thenReturn(Optional.of(unchangedUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User changedUser = DataUtil.getTestUser(0);
        changedUser.setEmail(newEmail);
        changedUser.setUsername(null);
        changedUser.setPassword(null);
        User updatedUser = userService.update(changedUser, unchangedUser.getId());

        assertEquals(updatedUser.getEmail(), newEmail);
    }

    @Test
    public void checkUpdate_whenBlankEmail_thenFailedValidationExceptionIsThrown() {
        User unchangedUser = DataUtil.getTestUser(0);
        Long id = unchangedUser.getId();
        when(userRepository.findByIdForUpdate(eq(id))).thenReturn(Optional.of(unchangedUser));
        User changedUser = DataUtil.getTestUser(0);
        changedUser.setEmail("");

        assertThrows(FailedValidationException.class, () -> userService.update(changedUser, id));
    }

    @Test
    public void checkUpdate_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        User user = DataUtil.getTestUser(0);
        Long id = user.getId();
        when(userRepository.findById(eq(id))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(user, id));
    }

    @Test
    public void checkUpdate_whenEmailAlreadyExists_thenThrowConstraintViolation() {
        User user = DataUtil.getTestUser(0);
        Long id = user.getId();
        when(userRepository.save(eq(user))).thenThrow(FailedValidationException.class);
        when(userRepository.findByIdForUpdate(eq(id))).thenReturn(Optional.of(user));
        assertThrows(FailedValidationException.class, () -> userService.update(user, id));
    }

    @Test
    public void checkDeleteByID_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        doThrow(new EntityNotFoundException()).when(userRepository).deleteById(0L);
        assertThrows(EntityNotFoundException.class, () -> userService.deleteById(0L));
    }
}
