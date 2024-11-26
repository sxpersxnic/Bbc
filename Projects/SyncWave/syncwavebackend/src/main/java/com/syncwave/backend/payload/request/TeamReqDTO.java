package com.syncwave.backend.payload.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class TeamReqDTO {

    private String name;
    private String initials;
    private String description;

}
