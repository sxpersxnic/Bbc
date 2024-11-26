package ch.bbcag.ticketshop.data.mapper;

import ch.bbcag.ticketshop.event.Event;
import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.person.PersonMapper;
import ch.bbcag.ticketshop.person.dto.PersonRequestDTO;
import ch.bbcag.ticketshop.person.dto.PersonResponseDTO;
import ch.bbcag.ticketshop.util.DataDTOUtil;
import ch.bbcag.ticketshop.util.DataUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonMapperTest {
    @Test
    public void fromDTO() {
        PersonRequestDTO dto = DataDTOUtil.getTestPersonRequestDTO();
        Person person = PersonMapper.fromDTO(dto);

        assertEquals(dto.getPassword(), person.getPassword());
        assertEquals(dto.getEmail(), person.getEmail());

    }

    @Test
    public void toDTO() {
        Person person = DataUtil.getTestPerson();
        PersonResponseDTO dto = PersonMapper.toDTO(person);

        assertEquals(dto.getId(), person.getId());
        assertEquals(dto.getEmail(), person.getEmail());
        for (Event event : person.getEvents()) {
            assertTrue(dto.getEventIds().contains(event.getId()));
        }


    }
}
