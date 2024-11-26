package com.syncwave.backend.repository;

import com.syncwave.backend.model.TaskUser;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskUserRepository extends JpaRepository<TaskUser, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "0")})
    @Query("SELECT t FROM task_user t WHERE t.id = :id")
    Optional<TaskUser> findByIdForUpdate(Long id);

    @Query("SELECT t FROM task_user t where t.user.id = :userId")
    List<TaskUser> findByUserId(@Param("userId") Long userId);

    @Query("SELECT tu FROM task_user tu WHERE tu.task.id = :taskId AND tu.user.id = :userId")
    Optional<TaskUser> findByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);
}
