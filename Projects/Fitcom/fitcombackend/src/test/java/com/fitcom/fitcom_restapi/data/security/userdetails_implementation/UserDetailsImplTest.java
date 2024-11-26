package com.fitcom.fitcom_restapi.data.security.userdetails_implementation;

import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsImpl;
import com.fitcom.fitcom_restapi.util.DataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDetailsImplTest {


    @Test
    void testConstructor() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(uuid, userDetails.getUuid());
        assertEquals(username, userDetails.getUsername());
        assertEquals(email, userDetails.getEmail());
        assertEquals(password, userDetails.getPassword());
        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    void testBuild() {
        User user = DataUtil.getTestUser(2);
        Role role = DataUtil.getTestRole(2);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        assertEquals(user.getUuid(), userDetails.getUuid());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testGetAuthorities() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(authorities, userDetails.getAuthorities());
    }

    @Test
    void testGetUuid() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(uuid, userDetails.getUuid());
    }

    @Test
    void testGetEmail() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(email, userDetails.getEmail());
    }

    @Test
    void testGetPassword() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(password, userDetails.getPassword());
    }

    @Test
    void testGetUsername() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails1 = new UserDetailsImpl(uuid, username, email, password, authorities);
        UserDetailsImpl userDetails2 = new UserDetailsImpl(uuid, username, email, password, authorities);

        assertEquals(userDetails1, userDetails2);
    }

    @Test
    void testHashCode() {
        UUID uuid = UUID.randomUUID();
        String username = "username";
        String email = "email@example.com";
        String password = "password";
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        UserDetailsImpl userDetails = new UserDetailsImpl(uuid, username, email, password, authorities);

        userDetails.hashCode();
    }
}