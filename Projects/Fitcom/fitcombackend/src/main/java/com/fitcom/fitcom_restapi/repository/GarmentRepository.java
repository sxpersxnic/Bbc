package com.fitcom.fitcom_restapi.repository;


import com.fitcom.fitcom_restapi.model.Garment;
import com.fitcom.fitcom_restapi.model.User;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GarmentRepository extends JpaRepository<Garment, UUID> {

    //: Find

    //: ByUUID

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
    @Query("SELECT g FROM Garment g WHERE g.uuid = :uuid")
    Optional<Garment> findByUUIDForUpdate(UUID uuid);


    @Query("SELECT g FROM Garment g WHERE g.uuid = :uuid")
    Optional<Garment> findByUUID(UUID uuid);

    @Query("SELECT u FROM User u WHERE u.uuid = :uuid")
    Optional<User> findOwnerByUUIDForMapping(UUID uuid);

    @Query("SELECT g FROM Garment g WHERE g.owner.uuid = :owner_uuid")
    List<Garment> findOwnedGarmentsByUUID(UUID owner_uuid);

    //: ByName
    @Query("SELECT g FROM Garment g WHERE g.name = :name")
    Optional<Garment> findByName(String name);

    //: Delete ByUUID
    @Modifying
    @Query("DELETE FROM Garment g WHERE g.uuid = :uuid")
    void deleteByUUID(UUID uuid);

    //: Exists ByName
    Boolean existsByName(String name);
}