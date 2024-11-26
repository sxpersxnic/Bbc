package ch.bbcag.ticketshop.event;

import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.ticket.Ticket;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Event {

    @Id // (2)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true) // (3)
    private String name;

    @Column(nullable = false)
    private LocalDate date; // (4)

    @Column(nullable = true)
    private String description;

    @ManyToOne(optional = false) // (5)
    @JoinColumn(name = "owner_id") // (6)
    private Person owner;

    @OneToMany(mappedBy = "event") // (7)
    private Set<Ticket> tickets;

    // Contructor
    public Event() {
    }

    // Getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Person getOwner() {
        return owner;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    // equals & hashCode (id)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
