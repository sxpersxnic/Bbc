package com.syncwave.backend.controller;


import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.UserMapper;
import com.syncwave.backend.payload.request.UserReqDTO;
import com.syncwave.backend.payload.response.UserResDTO;
import com.syncwave.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import static com.syncwave.backend.lib.constants.ControllerConstants.*;

@RestController
@RequestMapping(USERCONTROLLER_PATH)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(schema = @Schema(implementation = UserResDTO.class)))
    })
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();

        return ResponseEntity.ok(users.stream()
                .map(UserMapper::toDTO)
                .toList());
    }

    @GetMapping(ID_PATH)
    @Operation(summary = "Get a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResDTO.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content)
    })
    public ResponseEntity<?> findById(@Parameter(description = "Id of user to get") @PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }
    }

    @PatchMapping(UPDATE_PATH)
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResDTO.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the user",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The user to update") @RequestBody UserReqDTO dto, @PathVariable Long id) {
        try {
            User updateUser = UserMapper.fromDTO(dto);
            User savedUser = userService.update(updateUser, id);
            return ResponseEntity.ok(UserMapper.toDTO(savedUser));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the user");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }
    }

    @DeleteMapping(DELETE_PATH)
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of user to delete") @PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }
    }

    @GetMapping("/connected")
    public ResponseEntity<List<UserResDTO>> findByConnectedUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findConnectedUsers()
                                .stream()
                                .map(UserMapper::toDTO)
                                .toList()
                );
    }


    @GetMapping("/name/{username}")
    @Operation(summary = "Get a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = UserResDTO.class))),
            @ApiResponse(responseCode = "404", description = "User was not found",
                    content = @Content)
    })
    public ResponseEntity<?> findByUsername(@Parameter(description = "Id of user to get") @PathVariable String username) {
        try {
            User user = userService.findByUsername(username);
            return ResponseEntity.ok(UserMapper.toDTO(user));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found");
        }
    }

}

