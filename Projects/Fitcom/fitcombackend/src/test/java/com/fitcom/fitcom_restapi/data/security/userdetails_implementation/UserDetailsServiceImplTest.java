package com.fitcom.fitcom_restapi.data.security.userdetails_implementation;

import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.repository.UserRepository;
import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsImpl;
import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsServiceImpl;
import com.fitcom.fitcom_restapi.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testLoadUserByUsername_ExistingUser() {
        // Arrange
        User user = DataUtil.getTestUser(0);
        Role role = DataUtil.getTestRole(2);
        when(userRepo.findByUsername("username")).thenReturn(java.util.Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("username");

        // Assert
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getEmail(), ((UserDetailsImpl) userDetails).getEmail());
    }

    @Test
    void testLoadUserByUsername_NonExistingUser() {
        // Arrange
        when(userRepo.findByUsername("nonExistingUsername")).thenReturn(java.util.Optional.empty());

        // Act and Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonExistingUsername"));
        assertEquals("User with username: nonExistingUsername, was not found!", exception.getMessage());
    }
}
