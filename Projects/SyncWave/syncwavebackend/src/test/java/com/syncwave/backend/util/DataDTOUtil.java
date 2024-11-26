package com.syncwave.backend.util;

import com.syncwave.backend.payload.request.UserReqDTO;
import com.syncwave.backend.payload.response.UserResDTO;

import java.util.ArrayList;
import java.util.List;

public class DataDTOUtil {
    public static UserReqDTO getTestUserReqDTO(int index) {
        return getTestUserReqDTOs().get(index);
    }

    public static List<UserReqDTO> getTestUserReqDTOs() {
        List<UserReqDTO> reqList = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            UserReqDTO dto = new UserReqDTO();
            dto.setUsername("test_user" + i);
            reqList.add(dto);
        }

        return reqList;
    }

    public static UserResDTO getTestUserResDTO(int index) {
        return getTestUserResDTOs().get(index);
    }

    public static List<UserResDTO> getTestUserResDTOs() {
        List<UserResDTO> resList = new ArrayList<>();

        for (int i = 0; i <= 4; i++) {
            UserResDTO dto = new UserResDTO();
            dto.setId((long) i);
            dto.setUsername("test_user" + i);
            dto.setEmail("user" + i + "@localhost.com");
            resList.add(dto);
        }

        return resList;
    }
}
