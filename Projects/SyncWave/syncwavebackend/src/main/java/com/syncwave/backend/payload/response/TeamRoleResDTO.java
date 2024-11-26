package com.syncwave.backend.payload.response;

import com.syncwave.backend.payload.request.TeamRoleReqDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TeamRoleResDTO extends TeamRoleReqDTO {

    private Long id;
    private Set<Long> userIds;
}
