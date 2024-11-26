package com.syncwave.backend.payload.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SignInResDTO {
    private Long id;
    private List<String> roles;
    private String token;
    private String username;
    private String email;
}
