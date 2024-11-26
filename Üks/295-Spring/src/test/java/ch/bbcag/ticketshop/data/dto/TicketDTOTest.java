package ch.bbcag.ticketshop.data.dto;

import ch.bbcag.ticketshop.ticket.TicketDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TicketDTOTest {

    @Test
    public void constructor_hasEmptyConstructor() {
        boolean hasEmptyConstructor = Arrays.stream(TicketDTO.class.getConstructors())
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
        assertTrue(hasEmptyConstructor);
    }

    @Test
    public void idField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("id"));
    }

    @Test
    public void nameField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("name"));
    }

    @Test
    public void nameField_isAnnotatedWithNotBlank() {
        try {
            assertNotNull(TicketDTO.class
                    .getDeclaredField("name")
                    .getDeclaredAnnotation(NotBlank.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void descriptionField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("description"));
    }

    @Test
    public void amountField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("amount"));
    }

    @Test
    public void amountField_isAnnotatedWithMin() {
        try {
            assertEquals(0, TicketDTO.class
                    .getDeclaredField("amount")
                    .getDeclaredAnnotation(Min.class).value());
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    public void eventIdField_doesExist() {
        assertDoesNotThrow(() -> TicketDTO.class.getDeclaredField("eventId"));
    }

    @Test
    public void eventIdField_isAnnotatedWithNotNull() {
        try {
            assertNotNull(TicketDTO.class
                    .getDeclaredField("eventId")
                    .getDeclaredAnnotation(NotNull.class));
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

}
