package com.fitcom.fitcom_restapi.payload.mapper;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.payload.response.SignInResponseDTO;
import com.fitcom.fitcom_restapi.utils.RegexChecker;

public class SignInMapper {
    public static SignInResponseDTO toDTO(User user) {
        SignInResponseDTO dto = new SignInResponseDTO();
        dto.setUuid(user.getUuid());
        //TODO: dto.setJtwToken();
        //TODO: dto.setRole(user.getRole());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static User fromDTO(SignInRequestDTO dto) {
        User user = new User();
        RegexChecker.checkSignInPrincipal(dto, user);
        user.setPassword(dto.getPassword());
        return user;
    }
}
