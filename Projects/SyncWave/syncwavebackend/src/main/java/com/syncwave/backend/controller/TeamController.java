package com.syncwave.backend.controller;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Team;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.TeamMapper;
import com.syncwave.backend.payload.request.TeamReqDTO;
import com.syncwave.backend.payload.response.TeamResDTO;
import com.syncwave.backend.repository.TeamRepository;
import com.syncwave.backend.service.TeamService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.syncwave.backend.lib.constants.ControllerConstants.*;

@RestController
@RequestMapping(TEAMCONTROLLER_PATH)
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final TeamRepository teamRepository;

    public TeamController(TeamService teamService, UserService userService, TeamRepository teamRepository) {
        this.teamService = teamService;
        this.userService = userService;
        this.teamRepository = teamRepository;
    }

    @GetMapping(TEAM_BY_USER_PATH)
    @Operation(summary = "Get a Teams by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teams found",
                content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "User doesnt have any team",
                    content = @Content)
    })
    public ResponseEntity<?> getTeamsByUserid(@PathVariable Long userId) {
        Optional<List<Team>> teams = teamService.findTeamsByUserId(userId);
        if (teams.isPresent()) {
            return ResponseEntity.ok(teams.get().stream().map(TeamMapper::toDTO).toList());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(TEAM_ID_PATH)
    @Operation(summary = "Get a Team by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found",
                content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.findById(id);
        if (team.isPresent()) {
            return ResponseEntity.ok(TeamMapper.toDTO(team.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(TEAM_NAME_PATH)
    @Operation(summary = "Get a Team by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found",
                content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getTeamByName(@PathVariable String name) {
        Optional<Team> team = teamService.findByName(name);
        if (team.isPresent()) {
            return ResponseEntity.ok(TeamMapper.toDTO(team.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    @Operation(summary = "Delete a Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Team was deleted successfully",
                content = @Content),
            @ApiResponse(responseCode = "404", description = "Team could not be deleted",
                content = @Content)
    })
    public ResponseEntity<?> delete(@Parameter(description = "Id of Team to delete") Long id) {
        try {
            teamService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team was not found");
        }
    }


    @PostMapping
    @Operation(summary = "Create a Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team was created successfully",
                content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "409", description = "There was a conflict while creating the Team",
                content = @Content)
    })
    public ResponseEntity<?> create(@Parameter(description = "The Team to create") @RequestBody TeamReqDTO dto) {
        try {
            Team team = TeamMapper.fromDTO(dto);
            System.out.println(team.getMembers());
            User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            team.addUser(user);
            teamService.create(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while creating the Team");
        }
    }

    @PutMapping(UPDATE_TEAM_PATH)
    @Operation(summary = "Update a Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team was updated successfully",
                content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team was not found",
                content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Team",
                content = @Content)
    })
    public ResponseEntity<?> update(@Parameter(description = "The Team to update") @RequestBody TeamReqDTO dto, @PathVariable Long id) {
        try {
            Team updateTeam = TeamMapper.fromDTO(dto);
            Team savedTeam = teamService.update(updateTeam, id);
            return ResponseEntity.ok(TeamMapper.toDTO(savedTeam));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There was a conflict while updating the Team");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team was not found");
        }
    }

    @GetMapping("/teams/usersId")
    @Operation(summary = "Get all Teams of fetching user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team found",
                    content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team doesnt exist",
                    content = @Content)
    })
    public ResponseEntity<?> getAllTeamsFromUser() {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.findTeamIdsByUsername(userName);
            return ResponseEntity.ok(userService.findTeamIdsByUsername(userName)
                    .stream()
                    .map(teamRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(TeamMapper::toDTO)
                    .toList());
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Something went wrong");
        }
    }

    /*
    class only for add user to team
     */
    public static class AddUserToTeamRequest{
        public Long teamId;
        public Long[] userIds;
    }

    @PutMapping("/teams/users/add")
    @Operation(summary = "Add user to Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Team was updated successfully",
                    content = @Content(schema = @Schema(implementation = TeamResDTO.class))),
            @ApiResponse(responseCode = "404", description = "Team was not found",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Team",
                    content = @Content)
    })
    public ResponseEntity<?> addTeamToUser(
            @Parameter(description = "The Team to update and users to add") @RequestBody AddUserToTeamRequest dto
    ) {
        try {
            Team team = teamService.findById(dto.teamId).orElseThrow(EntityNotFoundException::new);
            Set<Chat> chats = team.getChats();
            if (new HashSet<>(team.getMembers().stream().map(User::getUsername).toList()).contains(SecurityContextHolder.getContext().getAuthentication().getName()) && team.getOwner() == userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())) {
                for (Long userId : dto.userIds) {
                    User user = userService.findById(userId);
                    team.addUser(user);
                    for (Chat chat : chats) {
                            chat.addUser(user);
                    }
                }
                teamService.update(team, dto.teamId);
                return ResponseEntity.ok(TeamMapper.toDTO(team));
            }
            else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to add users to this team");
            }

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }
}

