package com.syncwave.backend.payload.request;

import com.syncwave.backend.model.Permission;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TeamRoleReqDTO {

    private String name;
    private Set<Permission> permissions;
    private Long teamIds;
}
