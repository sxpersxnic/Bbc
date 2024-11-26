package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Team;
import com.syncwave.backend.model.TeamRole;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.TeamReqDTO;
import com.syncwave.backend.payload.response.TeamResDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TeamMapper {

    public static Team fromDTO(TeamReqDTO teamReqDTO) {
        Team team = new Team();
        team.setName(teamReqDTO.getName());
        team.setInitials(teamReqDTO.getInitials());
        team.setDescription(teamReqDTO.getDescription());
        team.setMembers(new HashSet<>());
        return team;
    }

    public static TeamResDTO toDTO(Team team) {
        TeamResDTO teamResDTO = new TeamResDTO();
        teamResDTO.setId(team.getId());
        teamResDTO.setName(team.getName());
        teamResDTO.setInitials(team.getInitials());
        teamResDTO.setDescription(team.getDescription());
        Set<Long> set = new HashSet<>();
        for (User user : team.getMembers()) {
            Long id = user.getId();
            set.add(id);
        }
        teamResDTO.setMemberIds(set);
        teamResDTO.setChatIds(team.getChats().stream().map(Chat::getId).collect(Collectors.toSet()));
        teamResDTO.setOwnerId(team.getOwner().getId());
        teamResDTO.setTeamRoleIds(team.getTeamRoles().stream().map(TeamRole::getId).collect(Collectors.toSet()));
        return teamResDTO;
    }

}
