package com.syncwave.backend.service;

import com.syncwave.backend.model.TeamRole;
import com.syncwave.backend.repository.TeamRoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamRoleService {
    private final TeamRoleRepository teamRoleRepository;

    public TeamRoleService(TeamRoleRepository teamRoleRepository) {
        this.teamRoleRepository = teamRoleRepository;
    }

    public void save(TeamRole teamRole) {
        teamRoleRepository.save(teamRole);
    }

    public void deleteById(Long id) {
        teamRoleRepository.deleteById(id);
    }

    public Optional<Set<TeamRole>> findByTeamID(Long teamID){
        return teamRoleRepository.findByTeamID(teamID);
    }

    public TeamRole findById(Long id) {
        return teamRoleRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public TeamRole findByNameInGivenTeam(String name, Long teamId) {
        return teamRoleRepository.findByNameInGivenTeam(name, teamId).orElseThrow(EntityNotFoundException::new);
    }

    public TeamRole updateTeamrole(TeamRole teamRole) {
        return teamRoleRepository.save(teamRole);
    }

    public List<TeamRole> findAll(){
        return teamRoleRepository.findAll();
    }


}
