package com.fitcom.fitcom_restapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "garment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "uuid"),
                @UniqueConstraint(columnNames = "name")
        })
public class Garment {

    // <------------ FIELDS ------------> //

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false, length = 36)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ECategory category;

    @NotBlank
    @Size(max = 255)
    @Column(name = "color", nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_uuid", nullable = false)
    private User owner;

    // <------------ CONSTRUCTORS ------------> //

    public Garment() {}

    public Garment(UUID uuid, String name, ECategory category, String color, User owner) {
        this.uuid = uuid;
        this.name = name;
        this.category = category;
        this.color = color;
        this.owner = owner;
    }
    // <------------ GETTER & SETTER ------------> //

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // <------------ EQUALS & HASHCODE ------------> //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Garment garment = (Garment) o;
        return Objects.equals(uuid, garment.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
