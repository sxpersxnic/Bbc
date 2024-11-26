package ch.bbcag.ticketshop.person;

import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.role.Role;

import java.util.List;

public class PersonMapper {
    public static PersonResponseDTO toDTO(Person person) {
        PersonResponseDTO dto = new PersonResponseDTO();

        dto.setId(person.getId());
        dto.setEmail(person.getEmail());

        List<Integer> eventIds = person.getEvents()
                .stream()
                .map(Event::getId)
                .toList();
        dto.setEventIds(eventIds);

        List<Integer> roleIds = person.getAssignedRoles()
                .stream()
                .map(Role::getId)
                .toList();
        dto.setRoleIds(roleIds);
        return dto;
    }

    public static Person fromDTO(PersonRequestDTO dto) {
        Person person = new Person();

        person.setPassword(dto.getPassword());
        person.setEmail(dto.getEmail());
        return person;
    }
}
