package com.syncwave.backend.controller;

import com.syncwave.backend.model.Task;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.TaskMapper;
import com.syncwave.backend.payload.request.TaskReqDTO;
import com.syncwave.backend.payload.response.TaskResDTO;
import com.syncwave.backend.service.TaskService;
import com.syncwave.backend.service.TaskUserService;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.syncwave.backend.lib.constants.ControllerConstants.*;

@RestController
@RequestMapping(TASKCONTROLLER_PATH)
public class TaskController {
    
    private final TaskService taskService;
    private final UserService userService;
    private final TaskUserService taskUserService;

    public TaskController(TaskService taskService, UserService userService, TaskUserService taskUserService){
        this.taskService = taskService;
        this.userService = userService;
        this.taskUserService = taskUserService;
    }

    @GetMapping(TASK_BY_USER_PATH)
    @Operation(summary = "Get a Teams by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teams found",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "User doesnt have any task",
                    content = @Content)
    })
    public ResponseEntity<?> getTaskByCreatorId(@PathVariable Long userId) {
        Optional<Task> teams = taskService.findCreatorByUserId(userId);
        if (teams.isPresent()) {
            return ResponseEntity.ok(teams.stream().map(TaskMapper::toDTO).toList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(TASK_ID_PATH)
    @Operation(summary = "Get a Task by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.findById(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(TaskMapper.toDTO(task.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(TASK_NAME_PATH)
    @Operation(summary = "Get a Task by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getTaskByName(@PathVariable String name) {
        Optional<Task> task = taskService.findByName(name);
        if (task.isPresent()) {
            return ResponseEntity.ok(TaskMapper.toDTO(task.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task was deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task could not be deleted",
                    content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of Task to delete") Long id) {
        try {
            taskService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }
    }


    @PostMapping
    @Operation(summary = "Create a Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task was created successfully",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "409", description = "There was a conflict while creating the Task",
                    content = @Content)
    })
    public ResponseEntity<?> create(@Parameter(description = "The Task to create") @RequestBody TaskReqDTO dto) {
        try {
            Task task = TaskMapper.fromDTO(dto, taskUserService, taskService, userService);
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println(userName);
            User user = userService.findByUsername(userName);
            task.setCreator(user);
            task.setCreatedAt(LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapper.toDTO(taskService.create(task)));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while creating the Task");
        }
    }

    @PutMapping(UPDATE_TASK_PATH)
    @Operation(summary = "Update a Task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task was updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Task",
                    content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The Task to update") @RequestBody TaskReqDTO dto, @PathVariable Long id) {
        try {
            Task updateUser = TaskMapper.fromDTO(dto, taskUserService, taskService, userService);
            Task savedUser = taskService.update(updateUser, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(TaskMapper.toDTO(savedUser));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the Task");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task was not found");
        }
    }

    @GetMapping("/tasks/usersId")
    @Operation(summary = "Get all Teams of fetching user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found",
                    content = @Content(schema = @Schema(implementation = TaskResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Task doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getAllTasksFromUser() {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(userName);
            if (taskService.findAllByUserId(user.getId()).isPresent()) {
                return ResponseEntity.ok(taskService.findAllByUserId(user.getId()).get().stream().map(TaskMapper::toDTO).toList());
            }
            else {
                return ResponseEntity.notFound().build();
            }
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong so cry about it");
        }
    }
}
