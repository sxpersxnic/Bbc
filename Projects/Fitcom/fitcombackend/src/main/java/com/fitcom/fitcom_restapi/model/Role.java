package com.fitcom.fitcom_restapi.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uuid"),
                @UniqueConstraint(columnNames = "name")
        })
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", unique = true, nullable = false)
    private ERole name;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

    public Role() {}
    public Role(ERole name) {
        this.name = name;
    }

    public Role(UUID uuid, ERole name) {
        this.name = name;
        this.uuid = uuid;
    }
    public Role(UUID uuid, ERole name, Set<User> users) {
        this.uuid = uuid;
        this.name = name;
        this.users = users;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(uuid, role.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}