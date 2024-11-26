package ch.bbcag.ticketshop.event;

import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.ticket.Ticket;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventMapper {
    public static EventDTO toDTO(Event event) {
        EventDTO dto = new EventDTO();

        dto.setId(event.getId());
        dto.setName(event.getName());
        dto.setDescription(event.getDescription());
        dto.setDate(event.getDate());
        dto.setOwnerId(event.getOwner().getId());

        List<Integer> ticketIds = event
                .getTickets()
                .stream()
                .map(Ticket::getId)
                .toList();
        dto.setTicketIds(ticketIds);

        return dto;
    }

    public static Event fromDTO(EventDTO dto) {
        Event event = new Event();

        event.setId(dto.getId());
        event.setName(dto.getName());
        event.setDescription(dto.getDescription());
        event.setDate(dto.getDate());

        Person owner = new Person();
        owner.setId(dto.getOwnerId());
        event.setOwner(owner);

        Set<Ticket> tickets = dto.getTicketIds()
                .stream()
                .map(id -> {
                    Ticket ticket = new Ticket();
                    ticket.setId(id);
                    return ticket;
                })
                .collect(Collectors.toSet());
        event.setTickets(tickets);


        return event;
    }
}
