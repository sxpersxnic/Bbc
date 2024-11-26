package com.syncwave.backend.payload.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.Set;


@Setter
@Getter
@EqualsAndHashCode(of = {"title", "description"})
public class TaskReqDTO {
    private String title;
    private String description;
    private LocalDateTime dueAt;
    private Set<Long> userIds;
}
