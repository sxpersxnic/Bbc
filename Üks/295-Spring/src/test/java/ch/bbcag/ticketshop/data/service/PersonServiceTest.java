package ch.bbcag.ticketshop.data.service;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.person.PersonRepository;
import ch.bbcag.ticketshop.person.PersonService;
import ch.bbcag.ticketshop.role.Role;
import ch.bbcag.ticketshop.role.RoleRepository;
import ch.bbcag.ticketshop.util.DataUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void checkFindAll_whenPersonsExist_thenPersonsAreReturned() {
        List<Person> expectedPersons = DataUtil.getTestPersons();
        when(personRepository.findAll()).thenReturn(expectedPersons);

        List<Person> actualPersons = personService.findAll();

        assertEquals(expectedPersons.size(), actualPersons.size());
        for (int i = 0; i < expectedPersons.size(); i++) {
            Person expectedPerson = expectedPersons.get(i);
            Person actualPerson = actualPersons.get(i);

            assertEquals(expectedPerson.getId(), actualPerson.getId());
            assertEquals(expectedPerson.getEmail(), actualPerson.getEmail());
            assertEquals(expectedPerson.getPassword(), actualPerson.getPassword());
            assertEquals(expectedPerson.getEvents(), actualPerson.getEvents());
        }
    }

    @Test
    public void checkFindById_whenExistingId_thenPersonIsReturned() {
        Person expectedPerson = DataUtil.getTestPerson();
        when(personRepository.findById(eq(1))).thenReturn(Optional.of(expectedPerson));

        Person actualPerson = personService.findById(1);

        assertNotNull(actualPerson);
        assertEquals(expectedPerson.getId(), actualPerson.getId());
        assertEquals(expectedPerson.getEmail(), actualPerson.getEmail());
        assertEquals(expectedPerson.getPassword(), actualPerson.getPassword());
        assertEquals(expectedPerson.getEvents(), actualPerson.getEvents());
    }

    @Test
    public void checkFindById_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        when(personRepository.findById(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.findById(0));
    }

    @Test
    public void checkFindByEmail_whenExistingEmail_thenPersonIsReturned() {
        Person expectedPerson = DataUtil.getTestPerson();
        String email = expectedPerson.getEmail();
        when(personRepository.findPersonByEmail(eq(email))).thenReturn(Optional.of(expectedPerson));

        Person actualPerson = personService.findByEmail(email);

        assertNotNull(actualPerson);
        assertEquals(expectedPerson.getId(), actualPerson.getId());
        assertEquals(expectedPerson.getEmail(), actualPerson.getEmail());
        assertEquals(expectedPerson.getPassword(), actualPerson.getPassword());
        assertEquals(expectedPerson.getEvents(), actualPerson.getEvents());
    }

    @Test
    public void checkFindByEmail_whenNonExistingEmail_thenEntityNotFound() {
        when(personRepository.findPersonByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.findByEmail("test@test.com"));
    }

    @Test
    public void checkCreate_whenValidPerson_thenPersonIsReturned() {
        Person expectedPerson = DataUtil.getTestPerson();
        Role testRole = DataUtil.getTestRole();
        when(personRepository.save(expectedPerson)).thenReturn(expectedPerson);
        when(roleRepository.findByName("USER")).thenReturn(testRole);
        when(passwordEncoder.encode(expectedPerson.getPassword())).thenReturn("encPassword");
        Person actualPerson = personService.create(expectedPerson);

        assertNotNull(actualPerson);
        assertEquals(expectedPerson.getId(), actualPerson.getId());
        assertEquals(expectedPerson.getEmail(), actualPerson.getEmail());
        assertEquals("encPassword", actualPerson.getPassword());
        assertEquals(expectedPerson.getEvents(), actualPerson.getEvents());
    }

    @Test
    public void checkCreate_whenEmailAlreadyExisting_thenThrowConstraintViolation() {
        Person person = DataUtil.getTestPerson();
        when(personRepository.save(eq(person))).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> personService.create(person));
    }

    @Test
    public void checkUpdate_whenValidPerson_thenChangedPersonIsReturned() {
        String newEmail = "test@test.com";

        Person unchangedPerson = DataUtil.getTestPerson();
        when(personRepository.findById(eq(1))).thenReturn(Optional.of(unchangedPerson));
        when(personRepository.save(any(Person.class))).thenAnswer(i -> i.getArgument(0));

        Person changedPerson = DataUtil.getTestPerson();
        changedPerson.setEmail(newEmail);
        Person updatedPerson = personService.update(changedPerson, 1);

        assertEquals(updatedPerson.getEmail(), newEmail);
    }

    @Test
    public void checkUpdate_whenBlankEmail_thenFailedValidationExceptionIsThrown() {
        Person unchangedPerson = DataUtil.getTestPerson();
        when(personRepository.findById(eq(1))).thenReturn(Optional.of(unchangedPerson));

        Person changedPerson = DataUtil.getTestPerson();
        changedPerson.setEmail("");

        assertThrows(FailedValidationException.class, () -> personService.update(changedPerson, 1));
    }

    @Test
    public void checkUpdate_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        Person person = DataUtil.getTestPerson();
        when(personRepository.findById(eq(0))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> personService.update(person, 0));
    }

    @Test
    public void checkUpdate_whenEmailAlreadyExists_thenThrowConstraintViolation() {
        Person person = DataUtil.getTestPerson();
        when(personRepository.save(eq(person))).thenThrow(ConstraintViolationException.class);
        when(personRepository.findById(eq(1))).thenReturn(Optional.of(person));
        assertThrows(ConstraintViolationException.class, () -> personService.update(person, 1));
    }

    @Test
    public void checkDeleteById_whenNonExistingId_thenEntityNotFoundExceptionIsThrown() {
        doThrow(new EntityNotFoundException()).when(personRepository).deleteById(0);
        assertThrows(EntityNotFoundException.class, () -> personService.deleteById(0));
    }

}
