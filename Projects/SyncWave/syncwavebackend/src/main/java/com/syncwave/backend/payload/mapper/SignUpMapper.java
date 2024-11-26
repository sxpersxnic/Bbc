package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.SignUpReqDTO;
import com.syncwave.backend.payload.response.SignUpResDTO;

public class SignUpMapper {
    public static SignUpResDTO toDTO(User src) {
        SignUpResDTO res = new SignUpResDTO();
        res.setId(src.getId());
        res.setUsername(src.getUsername());
        res.setEmail(src.getEmail());

        return res;
    }

    public static User fromDTO(SignUpReqDTO dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }
}
