package com.syncwave.backend.data.payload.response;

import com.syncwave.backend.payload.response.UserResDTO;
import com.syncwave.backend.util.DataDTOUtil;
import org.junit.jupiter.api.BeforeEach;

public class UserResDTOTest {

    Class<UserResDTO> clazz = UserResDTO.class;

    @BeforeEach
    void setUp() {
        UserResDTO dto1 = DataDTOUtil.getTestUserResDTO(0);
        UserResDTO dto2 = DataDTOUtil.getTestUserResDTO(0);
    }
}
