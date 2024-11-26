package com.syncwave.backend.controller;

import com.syncwave.backend.model.TaskUser;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.TaskUserMapper;
import com.syncwave.backend.payload.response.TaskResDTO;
import com.syncwave.backend.payload.response.TaskUserResDTO;
import com.syncwave.backend.service.TaskUserService;
import com.syncwave.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.syncwave.backend.lib.constants.ControllerConstants.TASKUSERCONTROLLER_PATH;

@RestController
@RequestMapping(TASKUSERCONTROLLER_PATH)
public class TaskUserController {
    
    private final TaskUserService taskUserService;
    private final UserService userService;
    
    public TaskUserController(TaskUserService taskUserService,UserService userService) {
        this.taskUserService = taskUserService;
        this.userService = userService;
    }

    @GetMapping("/taskuserId")
    @Operation(summary = "Get a TaskUser by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TaskUser found",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "TaskUser doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getAllTasksByUser() {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userName);
            List<TaskUser> taskUsers = taskUserService.findTasksByUserId(user.getId());
            List<TaskUserResDTO> tasksDTO = taskUsers.stream().map(TaskUserMapper::toDTO).toList();
            return ResponseEntity.status(HttpStatus.OK).body(tasksDTO);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskUser was not found");
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a TaskUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TaskUser was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "TaskUser could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of TaskUser to delete") Long id) {
        try {
            taskUserService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TaskUser was not found");
        }
    }

}
