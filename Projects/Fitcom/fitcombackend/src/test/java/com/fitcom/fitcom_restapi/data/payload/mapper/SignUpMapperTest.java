package com.fitcom.fitcom_restapi.data.payload.mapper;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.mapper.SignUpMapper;
import com.fitcom.fitcom_restapi.payload.request.SignUpRequestDTO;
import com.fitcom.fitcom_restapi.payload.response.SignUpResponseDTO;
import com.fitcom.fitcom_restapi.util.DataDTOUtil;
import com.fitcom.fitcom_restapi.util.DataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SignUpMapperTest {
    @Test
    public void fromDTO() {
        SignUpRequestDTO dto = DataDTOUtil.getTestSignUpRequestDTO();
        User user = SignUpMapper.fromDTO(dto);

        assertEquals(dto.getPassword(), user.getPassword());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());

    }

    @Test
    public void toDTO() {
        User user = DataUtil.getTestUser(0);
        SignUpResponseDTO dto = SignUpMapper.toDTO(user);

        assertEquals(dto.getUuid(), user.getUuid());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(dto.getCreateTime(), user.getCreateTime());
        assertNull(user.getUpdateTime());

    }
}
