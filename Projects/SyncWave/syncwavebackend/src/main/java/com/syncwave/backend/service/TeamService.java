package com.syncwave.backend.service;

import com.syncwave.backend.config.SecurityConfiguration;
import com.syncwave.backend.model.Permission;
import com.syncwave.backend.model.Team;
import com.syncwave.backend.model.TeamRole;
import com.syncwave.backend.model.User;
import com.syncwave.backend.repository.PermissionRepository;
import com.syncwave.backend.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final PermissionRepository permissionRepository;
    private final TeamRoleService teamRoleService;
    private final ChatService chatService;
    private final SecurityConfiguration securityConfiguration;

    public TeamService(TeamRepository teamRepository, UserService userService, PermissionRepository permissionRepository, TeamRoleService teamRoleService, ChatService chatService, SecurityConfiguration securityConfiguration) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.permissionRepository = permissionRepository;
        this.teamRoleService = teamRoleService;
        this.chatService = chatService;
        this.securityConfiguration = securityConfiguration;
    }

    public Optional<Team> findByName(String name) {
        return teamRepository.findByName(name);
    }

    public Optional<List<Team>> findTeamsByUserId(Long userId) {
        return teamRepository.findTeamsByMembersContains(userService.findById(userId));
    }

    public void deleteById(Long id) {
        teamRepository.deleteById(id);
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public void create(Team team) {
        try {
            User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            team.setOwner(user);
            team.addUser(user);
            teamRepository.save(team);
            TeamRole teamRole = new TeamRole();
            teamRole.setName("Owner");
            teamRole.setTeam(team);
            teamRole.setUsers(Set.of(user));

            Permission permission = permissionRepository.findById(1L).get();
            teamRole.setPermissions(Collections.singleton(permission));

            teamRoleService.save(teamRole);
        } catch (Exception e) {
            teamRepository.deleteById(team.getId());
            e.printStackTrace();
        }

    }

    @Transactional
    public Team update(Team team,Long id) {
        Team oldTeam = teamRepository.findByIdForUpdate(id).get();
        oldTeam.setName(team.getName());
        oldTeam.setInitials(team.getInitials());
        return teamRepository.save(oldTeam);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}


