package com.syncwave.backend.repository;

import com.syncwave.backend.model.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRoleRepository extends JpaRepository<TeamRole, Long> {

    @Query("SELECT r FROM teamrole r " +
            "JOIN r.team t WHERE t.id = :teamId " +
            "AND r.name = :name")
    Optional<TeamRole> findByNameInGivenTeam(String name, Long teamId);

    @Query("select r FROM teamrole r join r.team t where t.id = :teamId")
    Optional<Set<TeamRole>> findByTeamID(Long teamId);

}
