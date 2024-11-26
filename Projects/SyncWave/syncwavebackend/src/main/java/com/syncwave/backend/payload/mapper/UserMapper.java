package com.syncwave.backend.payload.mapper;


import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.UserReqDTO;
import com.syncwave.backend.payload.response.UserResDTO;

public class UserMapper {
    public static UserResDTO toDTO(User u) {
        UserResDTO dto = new UserResDTO();

        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());

        return dto;
    }

    public static User fromDTO(UserReqDTO dto) {
        User u = new User();
        u.setUsername(dto.getUsername());
        u.setEmail(dto.getEmail());
        return u;
    }

}

