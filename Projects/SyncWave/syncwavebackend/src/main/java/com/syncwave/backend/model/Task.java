package com.syncwave.backend.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "assignedTasks")
public class Task {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "due_at", nullable = false)
    private LocalDateTime dueAt;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(optional = false, cascade=CascadeType.ALL)
    @JoinColumn(name="creator_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "task")
    Set<TaskUser> assignedTasks = new HashSet<>();

    public Task(Long taskId) {
        this.id = taskId;
    }
}
