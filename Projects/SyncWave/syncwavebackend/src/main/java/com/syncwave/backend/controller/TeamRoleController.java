package com.syncwave.backend.controller;

import com.syncwave.backend.service.TeamRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.syncwave.backend.lib.constants.ControllerConstants.TEAMROLECONTROLLER_PATH;

@RestController
@RequestMapping(TEAMROLECONTROLLER_PATH)
public class TeamRoleController {

    private final TeamRoleService teamRoleService;

    public TeamRoleController(TeamRoleService teamRoleService) {
        this.teamRoleService = teamRoleService;
    }

    @RequestMapping
    public ResponseEntity<?> getTeamRoles() {
        return ResponseEntity.ok(teamRoleService.findAll());
    }
}
