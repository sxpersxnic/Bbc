package ch.bbcag.ticketshop.data.repository;

import ch.bbcag.ticketshop.ticket.Ticket;
import ch.bbcag.ticketshop.ticket.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TicketRepositoryTest {

    @Test
    public void repository_isInterface() {
        assertTrue(TicketRepository.class.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(TicketRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isTicket() {
        assertTrue(Arrays.stream(TicketRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Ticket.class)));
    }

    @Test
    public void repositoryId_isInteger() {
        assertTrue(Arrays.stream(TicketRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Integer.class)));
    }

    @Test
    public void repositoryFindAllUnsold_exists() {
        assertDoesNotThrow(() -> TicketRepository.class.getDeclaredMethod("findAllUnsold"));
    }

    @Test
    public void repositoryFindAllUnsold_returnsIterable() {
        try {
            assertTrue(Iterable.class.isAssignableFrom(TicketRepository.class.getDeclaredMethod("findAllUnsold").getReturnType()));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindAllUnsold_returnsIterableOfTickets() {
        try {
            assertEquals(Ticket.class, ((ParameterizedType) TicketRepository.class.getDeclaredMethod("findAllUnsold").getGenericReturnType()).getActualTypeArguments()[0]);
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindAllUnsold_hasQueryAnnotation() {
        try {
            assertNotNull((TicketRepository.class.getDeclaredMethod("findAllUnsold").getAnnotation(Query.class)));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    @Test
    public void repositoryFindAllUnsold_isCorrectQuery() {
        try {
            assertTrue(TicketRepository.class.getDeclaredMethod("findAllUnsold").getAnnotation(Query.class).value().equalsIgnoreCase("select t from Ticket t where t.amount > 0"));
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

}
