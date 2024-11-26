package ch.bbcag.ticketshop.data.dto;

import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonRequestDTOTest {

    @Test
    public void constructor_hasEmptyConstructor() {
        boolean hasEmptyConstructor = Arrays.stream(PersonRequestDTO.class.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(hasEmptyConstructor);
    }

    @Test
    public void passwordField_doesExist() {
        assertDoesNotThrow(() -> PersonRequestDTO.class.getDeclaredField("password"));
    }

    @Test
    public void passwordField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(PersonRequestDTO.class
                    .getDeclaredField("password")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void doesExtendPersonResponseDTO() {
        assertEquals(PersonResponseDTO.class, PersonRequestDTO.class.getSuperclass());
    }

}
