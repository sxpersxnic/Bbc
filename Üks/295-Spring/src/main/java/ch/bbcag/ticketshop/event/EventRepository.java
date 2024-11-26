package ch.bbcag.ticketshop.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
        @Query("SELECT e FROM Event e WHERE e.name = :name")
        Optional<Event> findEventByName(String name);
}
