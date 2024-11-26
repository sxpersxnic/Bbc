package com.fitcom.fitcom_restapi.payload.mapper;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;
import com.fitcom.fitcom_restapi.payload.response.SignUpResponseDTO;

public class SignUpMapper {

    public static SignUpResponseDTO toDTO(User user) {
        SignUpResponseDTO dto = new SignUpResponseDTO();
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreateTime(user.getCreateTime());
        return dto;
    }

    public static User fromDTO(SignUpRequestDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }
}
