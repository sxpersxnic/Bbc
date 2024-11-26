package com.syncwave.backend.payload.response;

import com.syncwave.backend.model.enums.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TaskUserResDTO {

    private Long id;
    private TaskResDTO task;
    private Long userId;
    private TaskStatus status;

}
