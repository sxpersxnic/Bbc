package ch.bbcag.ticketshop.util;


import ch.bbcag.ticketshop.event.EventDTO;
import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import ch.bbcag.ticketshop.ticket.TicketDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataDTOUtil {

    public static PersonResponseDTO getTestPersonResponseDTO() {
        return getTestPersonResponseDTOs().get(0);
    }

    public static PersonRequestDTO getTestPersonRequestDTO() {
        PersonRequestDTO signUpDTO = new PersonRequestDTO();
        signUpDTO.setEmail("person1@foo.bar");
        signUpDTO.setPassword("password1");
        return signUpDTO;
    }

    public static List<PersonResponseDTO> getTestPersonResponseDTOs() {
        List<PersonResponseDTO> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            PersonResponseDTO person = new PersonResponseDTO();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setEventIds(new ArrayList<>());
            person.setRoleIds(new ArrayList<>());
            person.getRoleIds().add(3);
            person.getEventIds().add(i);
            personList.add(person);
        }
        return personList;
    }

    public static EventDTO getTestEventDTO() {
        return getTestEventDTOs().get(0);
    }

    public static List<EventDTO> getTestEventDTOs() {
        List<EventDTO> eventDTOs = new ArrayList<>();
        List<PersonResponseDTO> testPersonDTOs = getTestPersonResponseDTOs();

        for (int i = 1; i <= 4; i++) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setId(i);
            eventDTO.setOwnerId(testPersonDTOs.get(i - 1).getId());
            eventDTO.setName("Event" + i);
            eventDTO.setDescription("Description" + i);
            eventDTO.setDate(LocalDate.EPOCH);

            List<Integer> ticketSet = new ArrayList<>();
            ticketSet.add(i);

            eventDTO.setTicketIds(ticketSet);

            eventDTOs.add(eventDTO);
        }

        return eventDTOs;
    }

    public static TicketDTO getTestTicketDTO() {
        return getTestTicketDTOs().get(0);
    }

    public static List<TicketDTO> getTestTicketDTOs() {
        List<TicketDTO> tickets = new ArrayList<>();

        int eventId = 1;

        for (int i = 1; i <= 4; i++) {
            TicketDTO ticket = new TicketDTO();
            ticket.setId(i);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setEventId(eventId);
            ticket.setAmount(i);
            tickets.add(ticket);
        }

        return tickets;
    }

}
