package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Task;
import com.syncwave.backend.model.TaskUser;
import com.syncwave.backend.model.User;
import com.syncwave.backend.model.enums.TaskStatus;
import com.syncwave.backend.payload.request.TaskReqDTO;
import com.syncwave.backend.payload.response.TaskResDTO;
import com.syncwave.backend.service.TaskService;
import com.syncwave.backend.service.TaskUserService;
import com.syncwave.backend.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


public class TaskMapper {


    public static Task fromDTO(TaskReqDTO taskReqDTO, TaskUserService taskUserService, TaskService taskService, UserService userService){
        Task task = new Task();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(userName);
        task.setTitle(taskReqDTO.getTitle());
        task.setDescription(taskReqDTO.getDescription());
        task.setDueAt(taskReqDTO.getDueAt());
        task.setCreator(user);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskService.create(task);
        for (Long userId : taskReqDTO.getUserIds()) {
            TaskUser taskUser = new TaskUser();
            taskUser.setTask(savedTask);
            taskUser.setUser(userService.findById(userId));
            taskUser.setTaskStatus(TaskStatus.CURRENT);
            taskUserService.create(taskUser);
            savedTask.getAssignedTasks().add(taskUser);
        }
        return task;
    }

    public static TaskResDTO toDTO(Task task){
        TaskResDTO taskResDTO = new TaskResDTO();
        taskResDTO.setId(task.getId());
        taskResDTO.setTitle(task.getTitle());
        taskResDTO.setDescription(task.getDescription());
        taskResDTO.setCreatedAt(task.getCreatedAt());
        taskResDTO.setUserIds(task.getAssignedTasks().stream().map(TaskUser::getUser).map(User::getId).collect(Collectors.toSet()));
        taskResDTO.setDueAt(task.getDueAt());
        return taskResDTO;
    }
}
