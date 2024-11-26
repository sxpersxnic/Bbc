package ch.bbcag.ticketshop.ticket;

import ch.bbcag.ticketshop.event.Event;
import jakarta.persistence.*;

import java.util.Objects;

@Entity // (1)
public class Ticket {
    @Id // (2)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount; // (3)

    private String description; // (4)
    @Column(nullable = false, unique = true) // (5)
    private String name;

    @ManyToOne(optional = false) // (6)
    @JoinColumn(name = "event_id") // (7)
    private Event event;

    // Getter
    public Integer getId() {
        return id;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
