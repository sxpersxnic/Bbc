package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Team;
import com.syncwave.backend.model.TeamRole;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.TeamRoleReqDTO;
import com.syncwave.backend.payload.response.TeamRoleResDTO;

import java.util.stream.Collectors;

public class TeamRoleMapper {
    public static TeamRole fromDTO(TeamRoleReqDTO dto) {
        TeamRole teamRole = new TeamRole();
        teamRole.setName(dto.getName());
        teamRole.setTeam(new Team(dto.getTeamIds()));
        teamRole.setPermissions(dto.getPermissions());
        return teamRole;
    }

    public static TeamRoleResDTO toDTO(TeamRole teamRole) {
        TeamRoleResDTO dto = new TeamRoleResDTO();
        dto.setId(teamRole.getId());
        dto.setUserIds(teamRole.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
        dto.setName(teamRole.getName());
        dto.setTeamIds(teamRole.getTeam().getId());
        dto.setPermissions(teamRole.getPermissions());
        return dto;
    }
}
