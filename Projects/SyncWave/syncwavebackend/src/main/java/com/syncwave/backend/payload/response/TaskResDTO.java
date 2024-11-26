package com.syncwave.backend.payload.response;
import com.syncwave.backend.payload.request.TaskReqDTO;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskResDTO extends TaskReqDTO{

    private Long id;
    private LocalDateTime createdAt;

}
