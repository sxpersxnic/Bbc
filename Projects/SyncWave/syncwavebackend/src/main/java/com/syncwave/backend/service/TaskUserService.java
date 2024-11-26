package com.syncwave.backend.service;

import com.syncwave.backend.model.TaskUser;
import com.syncwave.backend.repository.TaskUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TaskUserService {
    
    private final TaskUserRepository taskUserRepository;
    
    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    public void deleteById(Long id) {
        taskUserRepository.deleteById(id);
    }

    public Optional<TaskUser> findById(Long id) {
        return taskUserRepository.findById(id);
    }

    public List<TaskUser> findTasksByUserId(Long userId) {
        return taskUserRepository.findByUserId(userId);
    }

    public TaskUser create(TaskUser taskUser) {
        Optional<TaskUser> existingTaskUser = taskUserRepository.findByTaskIdAndUserId(
                taskUser.getTask().getId(), taskUser.getUser().getId());

        if (existingTaskUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"User already has this task assigned");
        }
        return taskUserRepository.save(taskUser);
    }

    @Transactional
    public TaskUser update(TaskUser taskUser,Long id) {
        TaskUser oldTaskUser = taskUserRepository.findByIdForUpdate(id).get();
        oldTaskUser.setTaskStatus(taskUser.getTaskStatus());
        return taskUserRepository.save(oldTaskUser);
    }

}
