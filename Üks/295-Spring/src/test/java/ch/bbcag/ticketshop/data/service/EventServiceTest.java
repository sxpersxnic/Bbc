package ch.bbcag.ticketshop.service;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.event.EventRepository;
import ch.bbcag.ticketshop.event.EventService;
import ch.bbcag.ticketshop.util.DataUtil;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void checkFindAll_whenEventsExist_thenEventsAreReturned() {
        List<Event> expectedEvents = DataUtil.getTestEvents();
        when(eventRepository.findAll()).thenReturn(expectedEvents);

        List<Event> actualEvents = eventService.findAll();

        assertEquals(expectedEvents.size(), actualEvents.size());
        for (int i = 0; i < expectedEvents.size(); i++) {
            Event expectedEvent = expectedEvents.get(i);
            Event actualEvent = actualEvents.get(i);

            assertEquals(expectedEvent.getId(), actualEvent.getId());
            assertEquals(expectedEvent.getName(), actualEvent.getName());
            assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
            assertEquals(expectedEvent.getDate(), actualEvent.getDate());
            assertEquals(expectedEvent.getOwner(), actualEvent.getOwner());
            assertEquals(expectedEvent.getTickets(), actualEvent.getTickets());
        }
    }

    @Test
    public void checkFindById_whenExistingId_thenEventIsReturned() {
        Event expectedEvent = DataUtil.getTestEvent();
        when(eventRepository.findById(eq(1))).thenReturn(Optional.of(expectedEvent));

        Event actualEvent = eventService.findById(1);

        assertNotNull(actualEvent);
        assertEquals(expectedEvent.getId(), actualEvent.getId());
        assertEquals(expectedEvent.getName(), actualEvent.getName());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
        assertEquals(expectedEvent.getDate(), actualEvent.getDate());
        assertEquals(expectedEvent.getOwner(), actualEvent.getOwner());
        assertEquals(expectedEvent.getTickets(), actualEvent.getTickets());
    }

    @Test
    public void checkFindById_whenNotExistingId_thenEntityNotFoundExceptionIsThrown() {
        when(eventRepository.findById(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> eventService.findById(0));
    }

    @Test
    public void checkCreate_whenValidEvent_thenEventIsReturned() {
        Event expectedEvent = DataUtil.getTestEvent();
        when(eventRepository.save(expectedEvent)).thenReturn(expectedEvent);

        Event actualEvent = eventService.create(expectedEvent);

        assertNotNull(actualEvent);
        assertEquals(expectedEvent.getId(), actualEvent.getId());
        assertEquals(expectedEvent.getName(), actualEvent.getName());
        assertEquals(expectedEvent.getDescription(), actualEvent.getDescription());
        assertEquals(expectedEvent.getDate(), actualEvent.getDate());
        assertEquals(expectedEvent.getOwner(), actualEvent.getOwner());
        assertEquals(expectedEvent.getTickets(), actualEvent.getTickets());
    }

    @Test
    public void checkUpdate_whenValidEvent_thenChangedEventIsReturned() {
        String newName = "NewEventName";

        Event unchangedEvent = DataUtil.getTestEvent();
        when(eventRepository.findById(eq(1))).thenReturn(Optional.of(unchangedEvent));
        when(eventRepository.save(any(Event.class))).thenAnswer(i -> i.getArgument(0));

        Event changedEvent = DataUtil.getTestEvent();
        changedEvent.setName(newName);
        Event updatedEvent = eventService.update(changedEvent, 1);

        assertEquals(updatedEvent.getName(), newName);
    }

    @Test
    public void checkUpdate_whenBlankName_thenFailedValidationExceptionIsThrown() {
        Event unchangedEvent = DataUtil.getTestEvent();
        when(eventRepository.findById(eq(1))).thenReturn(Optional.of(unchangedEvent));

        Event changedEvent = DataUtil.getTestEvent();
        changedEvent.setName("");

        assertThrows(FailedValidationException.class, () -> eventService.update(changedEvent, 1));
    }

    @Test
    public void checkUpdate_whenNotExistingId_thenEntityNotFoundExceptionIsThrown() {
        Event event = DataUtil.getTestEvent();
        when(eventRepository.findById(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> eventService.update(event, 0));
    }

    @Test
    public void checkDeleteById_whenNotExistingId_thenEntityNotFoundExceptionIsThrown() {
        doThrow(new EntityNotFoundException()).when(eventRepository).deleteById(0);
        assertThrows(EntityNotFoundException.class, () -> eventService.deleteById(0));
    }

}
