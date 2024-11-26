package ch.bbcag.ticketshop.data.mapper;

import ch.bbcag.ticketshop.ticket.Ticket;
import ch.bbcag.ticketshop.ticket.TicketDTO;
import ch.bbcag.ticketshop.ticket.TicketMapper;
import ch.bbcag.ticketshop.util.DataDTOUtil;
import ch.bbcag.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicketMapperTest {
    @Test
    public void fromDTO() {
        TicketDTO dto = DataDTOUtil.getTestTicketDTO();
        Ticket ticket = TicketMapper.fromDTO(dto);

        assertEquals(dto.getId(), ticket.getId());
        assertEquals(dto.getAmount(), ticket.getAmount());
        assertEquals(dto.getEventId(), ticket.getEvent().getId());
        assertEquals(dto.getDescription(), ticket.getDescription());
        assertEquals(dto.getName(), ticket.getName());
    }

    @Test
    public void toDTO() {
        Ticket ticket = DataUtil.getTestTicket();
        TicketDTO dto = TicketMapper.toDTO(ticket);

        assertEquals(dto.getId(), ticket.getId());
        assertEquals(dto.getAmount(), ticket.getAmount());
        assertEquals(dto.getEventId(), ticket.getEvent().getId());
        assertEquals(dto.getDescription(), ticket.getDescription());
        assertEquals(dto.getName(), ticket.getName());
    }
}
