package com.fitcom.fitcom_restapi.repository;

import com.fitcom.fitcom_restapi.model.ERole;
import com.fitcom.fitcom_restapi.model.Role;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
    @Query("SELECT r FROM Role r WHERE r.uuid = :uuid")
    Optional<Role> findByUUIDForUpdate(@Param("uuid") UUID uuid);

    @Query("SELECT r FROM Role r WHERE r.uuid = :uuid")
    Optional<Role> findByUUID(@Param("uuid") UUID uuid);

    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role findByName(@Param("name") ERole name);

    @Modifying
    @Query("DELETE FROM Role r WHERE r.uuid = :uuid")
    void deleteByUUID(@Param("uuid") UUID uuid);


    //: Exists

    //: ByName
    Boolean existsByName(ERole name);
}