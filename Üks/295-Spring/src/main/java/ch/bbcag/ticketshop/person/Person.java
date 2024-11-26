package ch.bbcag.ticketshop.person;

import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.role.Role;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity // (1)
public class Person {
    @Id // (2)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true) // (3)
    private String email;

    @Column(nullable = false) // (4)
    private String password;
    @OneToMany(mappedBy = "owner") // (5)
    private Set<Event> events;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> assignedRoles = new HashSet<>();

    public Person(Integer id) {
    }
    public Person(){}

    // Getter
    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Set<Role> getAssignedRoles() {
        return assignedRoles;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public void setAssignedRoles(Set<Role> assignedRoles) {
        this.assignedRoles = assignedRoles;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
