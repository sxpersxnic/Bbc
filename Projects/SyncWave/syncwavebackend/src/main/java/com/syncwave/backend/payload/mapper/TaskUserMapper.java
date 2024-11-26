package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Task;
import com.syncwave.backend.model.TaskUser;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.TaskUserReqDTO;
import com.syncwave.backend.payload.response.TaskUserResDTO;

public class TaskUserMapper {

    public static TaskUser fromDTO(TaskUserReqDTO taskUserReqDTO){
        TaskUser taskUser = new TaskUser();
        if (taskUserReqDTO.getTaskId() != null) {
            taskUser.setTask(new Task(taskUserReqDTO.getTaskId()));
        }
        if (taskUserReqDTO.getUserId() != null) {
            taskUser.setUser(new User(taskUserReqDTO.getUserId()));
        }
        taskUser.setTaskStatus(taskUserReqDTO.getTaskStatus());
        return taskUser;
    }

    public static TaskUserResDTO toDTO(TaskUser taskUser){
        TaskUserResDTO taskUserResDTO = new TaskUserResDTO();
        if (taskUser.getTask() != null) {
            taskUserResDTO.setTask(TaskMapper.toDTO(taskUser.getTask()));
        }
        if (taskUser.getUser() != null) {
            taskUserResDTO.setUserId(taskUser.getUser().getId());
        }
        taskUserResDTO.setStatus(taskUser.getTaskStatus());
        taskUserResDTO.setId(taskUser.getId());
        return taskUserResDTO;
    }
}
