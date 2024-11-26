package com.syncwave.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "initials")
    private String initials;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> members;

    @OneToMany(mappedBy = "team")
    private Set<Chat> chats;


    @OneToMany(mappedBy = "team")
    private Set<TeamRole> teamRoles;

    @ManyToOne
    private User owner;

    public Team(Long id){
        this.id = id;
    }

    public void addUser(User user) {
        members.add(user);
    }
}
