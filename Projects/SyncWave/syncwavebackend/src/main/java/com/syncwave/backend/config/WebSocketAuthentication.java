package com.syncwave.backend.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.syncwave.backend.model.User;
import com.syncwave.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class WebSocketAuthentication {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);
    private static final List<User> connectedUsers = new ArrayList<>();

    private final UserRepository userRepository;

    public WebSocketAuthentication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails authenticateRequest(AbstractWebSocketRequest webSocketRequest) {
        try {
            JWT jwt = JWTParser.parse(webSocketRequest.getToken());
            return new org.springframework.security.core.userdetails.User(jwt.getJWTClaimsSet().getSubject(), "", Collections.emptyList());
        } catch (ParseException e) {
            throw new AccessDeniedException("Invalid jwt");
        }
    }

    @MessageMapping("/auth")
    public void authenticate(@Payload WebSocketAuthRequest authRequest) {
        LOGGER.info("User connecting");
        UserDetails userDetails = authenticateRequest(authRequest);
        LOGGER.info(STR."User '\{userDetails.getUsername()}' successfully connected");
        connectedUsers.add(userRepository.findByUsername(userDetails.getUsername()).orElseThrow(EntityNotFoundException::new));
    }

}
