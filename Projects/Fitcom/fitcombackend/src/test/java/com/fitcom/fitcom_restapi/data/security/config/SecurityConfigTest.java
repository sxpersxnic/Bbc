package com.fitcom.fitcom_restapi.data.security.config;

import com.fitcom.fitcom_restapi.security.config.SecurityConfig;
import com.fitcom.fitcom_restapi.security.jwt.AuthEntryPointJwt;
import com.fitcom.fitcom_restapi.security.jwt.AuthTokenFilter;
import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private AuthEntryPointJwt unauthorizedHandler;

    @Mock
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private HttpSecurity httpSecurity;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.exceptionHandling(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn((DefaultSecurityFilterChain) mock(SecurityFilterChain.class));
    }

    @Test
    void authenticationJwtTokenFilter_ShouldReturnAuthTokenFilter() {
        AuthTokenFilter authTokenFilter = securityConfig.authenticationJwtTokenFilter();
        assertNotNull(authTokenFilter);
    }

    @Test
    void passwordEncoder_ShouldReturnPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
    }

    //@Test
    //void authenticationProvider_ShouldSetUserDetailsServiceAndPasswordEncoder() {
    //    securityConfig.authenticationProvider();
    //    verify(userDetailsService, times(1)).setPasswordEncoder(any(PasswordEncoder.class));
    //}

    @Test
    void authManager_ShouldReturnAuthenticationManager() throws Exception {
        AuthenticationManager authManager = securityConfig.authManager(authenticationConfiguration);
        assertNotNull(authManager);
    }

    @Test
    void filterChain_ShouldConfigureHttpSecurity() throws Exception {
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity);
        assertNotNull(filterChain);
        verify(httpSecurity, times(1)).csrf(any());
        verify(httpSecurity, times(1)).exceptionHandling(any());
        verify(httpSecurity, times(1)).sessionManagement(any());
        verify(httpSecurity, times(1)).authorizeHttpRequests(any());
        verify(httpSecurity, times(1)).authenticationProvider(any());
        verify(httpSecurity, times(1)).addFilterBefore(any(AuthTokenFilter.class), eq(UsernamePasswordAuthenticationFilter.class));
    }
}
