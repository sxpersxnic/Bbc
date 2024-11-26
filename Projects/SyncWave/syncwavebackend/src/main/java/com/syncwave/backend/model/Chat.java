package com.syncwave.backend.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_group", nullable = false)
    private boolean isGroup;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "chat_user",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "chat_id")
    private List<Message> messages;

    @ManyToOne
    private Team team;

    public Chat(Long id, List<User> users, String name, boolean isGroup) {
        this.id = id;
        this.users = users;
        this.name = name;
        this.isGroup = isGroup;
    }

    public Chat(Long id) {
        this.id = id;
    }

    public boolean getIsGroup() {
        return isGroup;
    }

    public void addUser(User user) {
        users.add(user);
    }

}
