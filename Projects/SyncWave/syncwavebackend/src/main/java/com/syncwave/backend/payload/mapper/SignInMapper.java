package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.lib.validation.Validator;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.SignInReqDTO;
import com.syncwave.backend.payload.response.SignInResDTO;

public class SignInMapper {
    public static SignInResDTO toDTO(User src, String accessToken) {
        SignInResDTO dto = new SignInResDTO();

        dto.setId(src.getId());
        dto.setUsername(src.getUsername());
        dto.setEmail(src.getEmail());
        dto.setToken(accessToken);
        return dto;
    }

    public static User fromDTO(SignInReqDTO dto) {
        User u = new User();

        u.setPassword(dto.getPassword());
       Validator.setPrincipal(dto.getPrincipal(), u);

        return u;
    }
}
