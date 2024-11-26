package com.fitcom.fitcom_restapi.data.payload.mapper;

import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.payload.mapper.SignInMapper;
import com.fitcom.fitcom_restapi.payload.request.SignInRequestDTO;
import com.fitcom.fitcom_restapi.payload.response.SignInResponseDTO;
import com.fitcom.fitcom_restapi.util.DataDTOUtil;
import com.fitcom.fitcom_restapi.util.DataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignInMapperTest {

    @Test
    public void toDTO() {
        User user = DataUtil.getTestUser(0);
        SignInResponseDTO dto = SignInMapper.toDTO(user);
        assertEquals(dto.getUuid(), user.getUuid());
        assertEquals(dto.getUsername(), user.getUsername());
        assertEquals(dto.getEmail(), user.getEmail());
        //TODO: assertEquals(dto.getJwtToken(), *CALL METHOD TO RECEIVE ACCESS TOKEN*);
        //TODO: assertEquals(dto.getRole(), user.getRole());

    }

    @Test
    void fromDTO() {
        SignInRequestDTO dto = DataDTOUtil.getTestSignInRequestDTO();
        User user = SignInMapper.fromDTO(dto);

        assertNotNull(user);
        assertTrue(isValidPrincipal(dto.getPrincipal(), user));
        assertEquals(dto.getPassword(), user.getPassword());
    }

    private boolean isValidPrincipal(String principal, User user) {
        if (principal.contains("@")) {
            // Principal is an email
            return principal.equals(user.getEmail());
        } else {
            // Principal is a username
            return principal.equals(user.getUsername());
        }
    }
}
