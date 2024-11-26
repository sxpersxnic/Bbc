package com.syncwave.backend.service;

import com.syncwave.backend.model.Task;
import com.syncwave.backend.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public Optional<Task> findByName(String name) {
        return taskRepository.findByTitle(name);
    }

    public Optional<Task> findCreatorByUserId(Long userId) {
        return taskRepository.findCreatorByUserId(userId);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Optional<List<Task>> findAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Transactional
    public Task update(Task task,Long id) {
        Task oldTask = taskRepository.findByIdForUpdate(id).get();
        oldTask.setTitle(task.getTitle());
        oldTask.setDescription(task.getDescription());
        oldTask.setUpdatedAt(task.getUpdatedAt());
        oldTask.setDueAt(task.getDueAt());
        return taskRepository.save(oldTask);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}

