package ch.bbcag.ticketshop.data.dto;

import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonResponseDTOTest {

    @Test
    public void constructor_hasEmptyConstructor() {
        boolean hasEmptyConstructor = Arrays.stream(PersonResponseDTO.class.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(hasEmptyConstructor);
    }

    @Test
    public void idField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("id"));
    }

    @Test
    public void emailField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("email"));
    }

    @Test
    public void emailField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(PersonResponseDTO.class
                    .getDeclaredField("email")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void eventIdsField_doesExist() {
        assertDoesNotThrow(() -> PersonResponseDTO.class.getDeclaredField("eventIds"));
    }

    @Test
    public void passwordField_doesNotExist() {
        assertThrows(NoSuchFieldException.class, () -> PersonResponseDTO.class.getDeclaredField("password"));
    }

}
