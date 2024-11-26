package com.syncwave.backend.controller;

import com.syncwave.backend.lib.exceptions.UserAlreadyExistsException;
import com.syncwave.backend.payload.request.SignInReqDTO;
import com.syncwave.backend.payload.request.SignUpReqDTO;
import com.syncwave.backend.payload.response.SignInResDTO;
import com.syncwave.backend.payload.response.SignUpResDTO;
import com.syncwave.backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.syncwave.backend.lib.constants.SecurityConstants.*;

@RestController
@RequestMapping(AUTHCONTROLLER_URL)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(SIGNUP_PATH)
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User was created successfully",
                    content = @Content(schema = @Schema(implementation = SignUpResDTO.class))),
            @ApiResponse(responseCode = "409", description = "User could not be created, username already in use",
                    content = @Content)
    })
    @SecurityRequirements
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpReqDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.SignUp(dto));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User could not be created, check if all every input is valid!");
        } catch (UserAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!");
        }
    }

    @PostMapping(SIGNIN_PATH)
    @Operation(summary = "Receive a token for BEARER authorization")
    @SecurityRequirements
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = SignInResDTO.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User to be signed in could not be found!",
                    content = @Content)
    })
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInReqDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.SignIn(dto));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials!");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User to be signed in could not be found!");
        }
    }

}
