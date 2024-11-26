package com.syncwave.backend.data.payload.request;

import com.syncwave.backend.payload.request.UserReqDTO;
import com.syncwave.backend.util.DataDTOUtil;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

import static com.syncwave.backend.util.TestConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.*;

public class UserReqDTOTest {

    private final Class<UserReqDTO> clazz = UserReqDTO.class;


    @Test
    public void usernameField_isNotAnnotatedWithNotBlank() {
        try {
            assertNull(clazz
                    .getDeclaredField(USERNAME)
                    .getDeclaredAnnotation(NotBlank.class)
            );
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void usernameField_isNotBlank() {
        UserReqDTO dto1 = DataDTOUtil.getTestUserReqDTO(0);
        UserReqDTO dto2 = DataDTOUtil.getTestUserReqDTO(0);
        assertNotNull(dto1.getUsername());
        assertNotEquals("", dto1.getUsername());
    }

    @Test
    void equalsMethod_worksCorrectly() {
        UserReqDTO dto1 = DataDTOUtil.getTestUserReqDTO(0);
        UserReqDTO dto2 = DataDTOUtil.getTestUserReqDTO(1);
        assertNotEquals(dto1, dto2);

        dto2.setUsername(dto1.getUsername());
        assertEquals(dto1, dto2);
    }

    @Test
    void hashCodeMethod_worksCorrectly() {
        UserReqDTO dto1 = DataDTOUtil.getTestUserReqDTO(0);
        UserReqDTO dto2 = DataDTOUtil.getTestUserReqDTO(1);
        assertNotEquals(dto1.hashCode(), dto2.hashCode());

        dto2.setUsername(dto1.getUsername());
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
