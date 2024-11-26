package com.fitcom.fitcom_restapi.util;

import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;

import java.util.ArrayList;
import java.util.List;

import static com.fitcom.fitcom_restapi.util.TestConstants.DEFAULT_ROLE;

public class DataDTOUtil {

    public static SignUpRequestDTO getTestSignUpRequestDTO() {
        return getTestSignUpRequestDTOs().getFirst();
    }

    public static SignUpRequestDTO getTestSignUpRequestDTOWithoutRole() {
        SignUpRequestDTO dto = getTestSignUpRequestDTOs().getFirst();
        dto.setRole(null);
        return dto;
    }
    public static List<SignUpRequestDTO> getTestSignUpRequestDTOs() {
        List<SignUpRequestDTO> dtoList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            SignUpRequestDTO dto = new SignUpRequestDTO("TestSignUpUsername" + i, "user" + i + ".test@localhost.com", "P@ssw0rd!" + i, DEFAULT_ROLE);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public static SignInRequestDTO getTestSignInRequestDTO() {
        return getTestSignInRequestDTOs().getFirst();
    }

    public static List<SignInRequestDTO> getTestSignInRequestDTOs() {
        List<SignInRequestDTO> dtoList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            SignInRequestDTO dto = new SignInRequestDTO("TestSignUpUsername" + i, "P@ssw0rd!" + i);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
