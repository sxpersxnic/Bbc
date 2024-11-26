package com.fitcom.fitcom_restapi.data.security.jwt;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.security.jwt.JwtUtils;
import com.fitcom.fitcom_restapi.security.userdetails_implementation.UserDetailsImpl;
import com.fitcom.fitcom_restapi.util.DataUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseCookie;
import org.springframework.mock.web.MockHttpServletRequest;

import java.security.Key;
import java.util.Date;

import static com.fitcom.fitcom_restapi.security.utils.SecurityConstants.*;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private Key key;

    @InjectMocks
    private JwtUtils jwtUtils;

    private UserDetailsImpl userDetails;
    private HttpServletRequest request;
    private final User user = DataUtil.getTestUser(0);

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.jwtSecret = JWT_SECRET;
        jwtUtils.jwtExpirationMs = parseInt(ACCESS_TOKEN_EXPIRATION_TIME); // 1 hour for testing
        jwtUtils.jwtCookie = ACCESS_TOKEN_NAME;

        userDetails = new UserDetailsImpl(
                user.getUuid(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                null // authorities not needed for JWT generation
        );

        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    void testGetJwtFromCookies_NoCookie() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        String jwt = jwtUtils.getJwtFromCookies(request);
        assertNull(jwt);
    }

    //G4
    @Test
    void getJwtFromCookies_ShouldReturnToken_WhenCookieExists() {
        Cookie cookie = new Cookie(jwtUtils.jwtCookie, "testToken");
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});

        String token = jwtUtils.getJwtFromCookies(request);
        assertEquals("testToken", token);
    }

    @Test
    void getJwtFromCookies_ShouldReturnNull_WhenCookieDoesNotExist() {
        when(request.getCookies()).thenReturn(null);

        String token = jwtUtils.getJwtFromCookies(request);
        assertNull(token);
    }


    @Test
    void getCleanJwtCookie_ShouldReturnCookieWithMaxAgeZero() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        assertNotNull(cookie);
        assertEquals(jwtUtils.jwtCookie, cookie.getName());
        assertEquals(API_ENDPOINT, cookie.getPath());
        assertEquals(0, cookie.getMaxAge().getSeconds());
        assertTrue(cookie.isHttpOnly());
    }

    @Test
    void getUsernameFromJwtToken_ShouldReturnUsername_WhenTokenIsValid() {
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        String username = jwtUtils.getUsernameFromJwtToken(token);
        assertEquals(userDetails.getUsername(), username);
    }

    @Test
    void validateJwtToken_ShouldReturnTrue_WhenTokenIsValid() {
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        assertTrue(jwtUtils.validateJwtToken(token));
    }
    @Test
    void generateTokenFromUsername_ShouldGenerateValidToken() {
        String token = jwtUtils.generateTokenFromUsername(userDetails.getUsername());
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateJwtCookie_ShouldReturnValidCookie() {
        ResponseCookie cookie = jwtUtils.generateJwtCookie(userDetails);
        assertNotNull(cookie);
        assertEquals(jwtUtils.jwtCookie, cookie.getName());
        assertEquals(API_ENDPOINT, cookie.getPath());
        assertTrue(cookie.getMaxAge().getSeconds() > 0);
        assertTrue(cookie.isHttpOnly());
    }


    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsInvalid() {
        String token = "invalidToken";
        assertFalse(jwtUtils.validateJwtToken(token));
    }
    @Test
    void testGetUsernameFromJwtToken() {
        String token = jwtUtils.generateTokenFromUsername("username");
        String username = jwtUtils.getUsernameFromJwtToken(token);
        assertEquals("username", username);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        String token = jwtUtils.generateTokenFromUsername("username");
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void testValidateJwtToken_InvalidToken() {
        assertFalse(jwtUtils.validateJwtToken("invalidToken"));
    }

    @Test
    void testGetJwtFromCookies() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie(ACCESS_TOKEN_NAME, "jwtToken"));

        String jwt = jwtUtils.getJwtFromCookies(request);
        assertEquals("jwtToken", jwt);
    }
    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsExpired() {
        // Arrange
        String username = userDetails.getUsername();
        Date now = new Date();
        Date pastDate = new Date(now.getTime() - 3600000); // 1 hour in the past
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(pastDate)
                .signWith(jwtUtils.key(), SignatureAlgorithm.HS256)
                .compact();

        // Act
        Throwable exception = assertThrows(
                ExpiredJwtException.class,
                () -> jwtUtils.validateJwtToken(expiredToken)
        );

        // Assert
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("expired"));
    }

    @Test
    void testGenerateJwtCookie() {
        User user = DataUtil.getTestUser(0);
        UserDetailsImpl userPrincipal = new UserDetailsImpl(user.getUuid(), user.getUsername(), user.getEmail(), user.getPassword(), null);

        ResponseCookie cookie = jwtUtils.generateJwtCookie(userPrincipal);
        assertEquals(ACCESS_TOKEN_NAME, cookie.getName());
        assertNotNull(cookie.getValue());
        assertEquals(API_ENDPOINT, cookie.getPath());
        assertEquals(86400, cookie.getMaxAge().getSeconds()); // Compare seconds
        assertTrue(cookie.isHttpOnly());
    }
    @Test
    void testGetCleanJwtCookie() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        assertEquals(ACCESS_TOKEN_NAME, cookie.getName());
        assertEquals("", cookie.getValue());
        assertEquals(API_ENDPOINT, cookie.getPath());
        assertEquals(0, cookie.getMaxAge().getSeconds());
        assertTrue(cookie.isHttpOnly());
    }

}
