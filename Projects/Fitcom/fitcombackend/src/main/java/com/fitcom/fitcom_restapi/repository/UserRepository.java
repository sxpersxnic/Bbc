package com.fitcom.fitcom_restapi.repository;

import com.fitcom.fitcom_restapi.model.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    //: Find

    //: ByUUID
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
    @Query("SELECT u FROM User u WHERE u.uuid = :uuid")
    Optional<User> findByUUIDForUpdate(@Param("uuid") UUID uuid);

    @Query("SELECT u FROM User u WHERE u.uuid = :uuid")
    Optional<User> findByUUID(@Param("uuid") UUID uuid);

    //: ByEmail
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    //: ByUsername
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    //: ByPrincipal -> Username OR Email
    @Query("SELECT u FROM User u WHERE u.email = :principal OR u.username = :principal")
    Optional<User> findByPrincipal(@Param("principal") String principal);

    //: Delete by UUID
    @Modifying
    @Query("DELETE FROM User u WHERE u.uuid = :uuid")
    void deleteByUUID(@Param("uuid") UUID uuid);


    //: Exists
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}