package com.syncwave.backend.payload.response;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public final class UserResDTO {
    private Long id;
    private String username;
    private String email;
}

