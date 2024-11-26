package com.fitcom.fitcom_restapi.data.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcom.fitcom_restapi.security.jwt.AuthEntryPointJwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.security.core.AuthenticationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthEntryPointJwtTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt;

    private ByteArrayOutputStream outputStream;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        outputStream = new ByteArrayOutputStream();
        mapper = new ObjectMapper();

        when(request.getServletPath()).thenReturn("/test/path");
        when(authException.getMessage()).thenReturn("Unauthorized error message");
        when(response.getOutputStream()).thenReturn(new DelegatingServletOutputStream(outputStream));
    }

    @Test
    void commence_ShouldSetUnauthorizedResponse() throws IOException, ServletException {
        authEntryPointJwt.commence(request, response, authException);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> responseBody = mapper.readValue(outputStream.toByteArray(), Map.class);

        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseBody.get("status"));
        assertEquals("Unauthorized", responseBody.get("error"));
        assertEquals("Unauthorized error message", responseBody.get("message"));
        assertEquals("/test/path", responseBody.get("path"));
    }
}
