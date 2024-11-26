package ch.bbcag.ticketshop.role;

import ch.bbcag.ticketshop.person.Person;

import java.util.List;

public class RoleMapper {
    public static RoleDTO toDTO(Role role) {
        RoleDTO dto = new RoleDTO();

        dto.setId(role.getId());
        dto.setName(role.getName());

        List<Integer> personIds = role.getAssignedPersons()
                .stream()
                .map(Person::getId)
                .toList();
        dto.setAssignedPersonIds(personIds);
        return dto;
    }
    public static Role fromDTO(RoleDTO dto) {
        Role role = new Role();

        role.setId(dto.getId());
        role.setName(dto.getName());
        return role;
    }
}
