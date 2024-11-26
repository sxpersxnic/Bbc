package com.syncwave.backend.payload.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserReqDTO {

    private String username;
    private String email;

}

