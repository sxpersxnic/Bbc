package com.syncwave.backend.data.lib.constants;

import com.syncwave.backend.lib.constants.SecurityConstants;
import org.junit.jupiter.api.Test;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityConstantsTest {

    @Test
    void secretKeySpec_isCorrect() {
        SecretKeySpec expectedKey = new SecretKeySpec(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8), SecurityConstants.ALGORITHM);
        assertEquals(expectedKey, SecurityConstants.SECRET_KEY_SPEC, "Expected SECRET_KEY_SPEC to match the provided secret and algorithm");
    }

    @Test
    void expirationTime_isCorrect() {
        long expectedExpirationTime = 864_000_000L; // 10 days
        assertEquals(expectedExpirationTime, SecurityConstants.EXPIRATION_TIME, "Expected EXPIRATION_TIME to be 10 days");
    }

    @Test
    void authUrls_isCorrect() {
        String expectedAuthUrls = "/api/v1/auth/**";
        assertEquals(expectedAuthUrls, SecurityConstants.AUTH_URLS, "Expected AUTH_URLS to match the provided path");
    }

    @Test
    void authControllerUrl_isCorrect() {
        String expectedAuthControllerUrl = "/api/v1/auth";
        assertEquals(expectedAuthControllerUrl, SecurityConstants.AUTHCONTROLLER_URL, "Expected AUTHCONTROLLER_URL to match the provided path");
    }

    @Test
    void signupPath_isCorrect() {
        String expectedSignupPath = "/signup";
        assertEquals(expectedSignupPath, SecurityConstants.SIGNUP_PATH, "Expected SIGNUP_PATH to match the provided path");
    }

    @Test
    void signinPath_isCorrect() {
        String expectedSigninPath = "/signin";
        assertEquals(expectedSigninPath, SecurityConstants.SIGNIN_PATH, "Expected SIGNIN_PATH to match the provided path");
    }

    @Test
    void signoutPath_isCorrect() {
        String expectedSignoutPath = "/signout";
        assertEquals(expectedSignoutPath, SecurityConstants.SIGNOUT_PATH, "Expected SIGNOUT_PATH to match the provided path");
    }

    @Test
    void apiDocumentationUrls_areCorrect() {
        String[] expectedApiDocumentationUrls = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};
        assertArrayEquals(expectedApiDocumentationUrls, SecurityConstants.API_DOCUMENTATION_URLS, "Expected API_DOCUMENTATION_URLS to match the provided paths");
    }

    @Test
    void authorizationHeaderName_isCorrect() {
        String expectedAuthorizationHeaderName = "Authorization";
        assertEquals(expectedAuthorizationHeaderName, SecurityConstants.AUTHORIZATION_HEADER_NAME, "Expected AUTHORIZATION_HEADER_NAME to match the provided header name");
    }
}

