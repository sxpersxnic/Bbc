package ch.bbcag.ticketshop.role;

import ch.bbcag.ticketshop.person.Person;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity // (1)
public class Role implements GrantedAuthority {
    @Id // (2)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "assignedRoles")
    private Set<Person> assignedPersons = new HashSet<>();

    // constructor
    public Role() {}

    // Getter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Person> getAssignedPersons() {
        return assignedPersons;
    }

    // Setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAssignedPersons(Set<Person> assignedPersons) {
        this.assignedPersons = assignedPersons;
    }

    // equals & hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
