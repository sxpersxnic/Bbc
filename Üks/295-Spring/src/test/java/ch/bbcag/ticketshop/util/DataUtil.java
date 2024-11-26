package ch.bbcag.ticketshop.util;

import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.role.Role;
import ch.bbcag.ticketshop.ticket.Ticket;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataUtil {

    public static Person getTestPerson() {
        return getTestPersons().get(0);
    }

    public static List<Person> getTestPersons() {
        List<Person> personList = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Person person = new Person();
            person.setId(i);
            person.setEmail("person" + i + "@foo.bar");
            person.setPassword("password" + i);

            Event event = new Event();
            Role role = new Role();

            event.setId(i);
            event.setDate(LocalDate.EPOCH);
            event.setOwner(person);
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setTickets(new HashSet<>());
            role.setId(3);
            role.setName("USER");
            person.setEvents(new HashSet<>());
            person.setAssignedRoles(new HashSet<>());
            person.getEvents().add(event);
            person.getAssignedRoles().add(role);
            personList.add(person);
        }
        return personList;
    }

    public static Event getTestEvent() {
        return getTestEvents().get(0);
    }

    public static List<Event> getTestEvents() {
        List<Event> events = new ArrayList<>();
        List<Person> persons = getTestPersons();

        for (int i = 1; i <= 4; i++) {
            Event event = new Event();
            event.setId(i);
            event.setOwner(persons.get(i - 1));
            event.setName("Event" + i);
            event.setDescription("Description" + i);
            event.setDate(LocalDate.EPOCH);

            Set<Ticket> ticketSet = new HashSet<>();
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setEvent(event);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setAmount(i - 1);
            ticketSet.add(ticket);

            event.setTickets(ticketSet);

            events.add(event);
        }

        return events;
    }

    public static Ticket getTestTicket() {
        return getTestTickets().get(0);
    }

    public static List<Ticket> getTestTickets() {
        List<Ticket> tickets = new ArrayList<>();

        Event event = new Event();
        event.setId(1);
        event.setName("Event1");
        event.setDescription("Description1");
        event.setOwner(getTestPerson());

        for (int i = 1; i <= 4; i++) {
            Ticket ticket = new Ticket();
            ticket.setId(i);
            ticket.setName("Ticket" + i);
            ticket.setDescription("Description" + i);
            ticket.setEvent(event);
            ticket.setAmount(i);
            tickets.add(ticket);
        }

        return tickets;
    }

    public static Role getTestRole() {
        return getTestRoles().get(0);
    }

    public static List<Role> getTestRoles() {
        List<Role> roles = new ArrayList<>();

        int roleId = 1;

        for (String roleName : List.of("MANAGER", "ADMIN", "USER")) {
            Role role = new Role();
            role.setName(roleName);
            role.setId(roleId++);
            roles.add(role);
        }
        return roles;
    }

}
