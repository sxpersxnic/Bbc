package ch.bbcag.ticketshop.data.dto;

import ch.bbcag.ticketshop.event.EventDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EventDTOTest {

    @Test
    public void constructor_hasEmptyConstructor() {
        boolean hasEmptyConstructor = Arrays.stream(EventDTO.class.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(hasEmptyConstructor);
    }

    @Test
    public void idField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("id"));
    }

    @Test
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("name"));
    }

    @Test
    public void nameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void descriptionField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("description"));
    }

    @Test
    public void ownerIdField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("ownerId"));
    }

    @Test
    public void ownerIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("ownerId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void dateField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("date"));
    }

    @Test
    public void dateField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(EventDTO.class
                    .getDeclaredField("date")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void ticketIdsField_doesExist() {
        assertDoesNotThrow(() -> EventDTO.class.getDeclaredField("ticketIds"));
    }

}
