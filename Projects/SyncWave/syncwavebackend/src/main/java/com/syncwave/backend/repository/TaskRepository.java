package com.syncwave.backend.repository;

import com.syncwave.backend.model.Task;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT t from Task t where t.title = :title")
    Optional<Task> findByTitle(String title);

    @Query("SELECT t from Task t where t.creator.id = :userId")
    Optional<List<Task>> findAllByUserId(Long userId);

    @Query("SELECT t from Task t Join t.creator u where u.id = :userId")
    Optional<Task> findCreatorByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
    @Query("SELECT t FROM Task t WHERE t.id = :id")
    Optional<Task> findByIdForUpdate(Long id);
}
