package com.syncwave.backend.payload.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TeamResDTO {

    private Long id;
    private String name;
    private String initials;
    private String description;
    private Set<Long> memberIds;
    private Set<Long> chatIds;
    private Long ownerId;
    private Set<Long> teamRoleIds;

}
