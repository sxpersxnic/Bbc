package ch.bbcag.ticketshop.data.mapper;

import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.event.EventDTO;
import ch.bbcag.ticketshop.event.EventMapper;
import ch.bbcag.ticketshop.ticket.Ticket;
import ch.bbcag.ticketshop.util.DataDTOUtil;
import ch.bbcag.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventMapperTest {

    @Test
    public void fromDTO() {
        EventDTO dto = DataDTOUtil.getTestEventDTO();
        Event event = EventMapper.fromDTO(dto);

        assertEquals(dto.getId(), event.getId());
        assertEquals(dto.getName(), event.getName());
        assertEquals(dto.getDescription(), event.getDescription());
        assertEquals(dto.getDate(), event.getDate());
        assertEquals(dto.getOwnerId(), event.getOwner().getId());
        assertEquals(dto.getTicketIds().size(), event.getTickets().size());

        for (Ticket ticket : event.getTickets()) {
            assertTrue(dto.getTicketIds().contains(ticket.getId()));
        }
    }

    @Test
    public void toDTO() {
        Event event = DataUtil.getTestEvent();
        EventDTO dto = EventMapper.toDTO(event);

        assertEquals(event.getId(), dto.getId());
        assertEquals(event.getName(), dto.getName());
        assertEquals(event.getDescription(), dto.getDescription());
        assertEquals(event.getDate(), dto.getDate());
        assertEquals(event.getOwner().getId(), dto.getOwnerId());
        assertEquals(event.getTickets().size(), dto.getTicketIds().size());

        for (Ticket ticket : event.getTickets()) {
            assertTrue(dto.getTicketIds().contains(ticket.getId()));
        }
    }

}
