package ch.bbcag.ticketshop.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r WHERE r.name LIKE CONCAT('%', :name, '%')")
    Role findByName(String name);
}
