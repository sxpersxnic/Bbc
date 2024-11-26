package com.syncwave.backend.model;

import com.syncwave.backend.model.enums.ActiveStatus;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(of = "username")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "chat_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> sentChats = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Team> teams;

    @ManyToMany(mappedBy = "users")
    private Set<TeamRole> teamRoles;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<TaskUser> taskUsers;

    public User() {
    }

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long id) {
        this.id = id;
    }
}
