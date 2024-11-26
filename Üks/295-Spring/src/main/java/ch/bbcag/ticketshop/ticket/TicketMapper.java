package ch.bbcag.ticketshop.ticket;

import ch.bbcag.ticketshop.event.Event;

public class TicketMapper {
    public static TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setAmount(ticket.getAmount());
        dto.setEventId(ticket.getEvent().getId());
        dto.setDescription(ticket.getDescription());
        dto.setName(ticket.getName());

        return dto;
    }

    public static Ticket fromDTO(TicketDTO dto) {
        Ticket ticket = new Ticket();

        ticket.setId(dto.getId());
        ticket.setAmount(dto.getAmount());
        ticket.setDescription(dto.getDescription());
        ticket.setName(dto.getName());

        Event event = new Event();
        event.setId(dto.getEventId());
        ticket.setEvent(event);
        return ticket;
    }
}
