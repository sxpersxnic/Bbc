package com.syncwave.backend.payload.request;

import com.syncwave.backend.model.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TaskUserReqDTO {

    @NotNull(message = "Task status cannot be null")
    private TaskStatus taskStatus;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Task ID cannot be null")
    private Long taskId;

}
