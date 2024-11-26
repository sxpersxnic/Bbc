package ch.bbcag.ticketshop.data.repository;

import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.person.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PersonRepositoryTest {

    @Test
    public void repository_isInterface() {
        assertTrue(PersonRepository.class.isInterface());
    }

    @Test
    public void repository_extendsJPARepository() {
        assertTrue(Arrays.asList(PersonRepository.class.getInterfaces()).contains(JpaRepository.class));
    }

    @Test
    public void repositoryEntity_isPerson() {
        assertTrue(Arrays.stream(PersonRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[0].equals(Person.class)));
    }

    @Test
    public void repositoryId_isInteger() {
        assertTrue(Arrays.stream(PersonRepository.class.getGenericInterfaces())
                .filter(t -> t instanceof ParameterizedType)
                .map(t -> ((ParameterizedType) t))
                .anyMatch(t -> t.getRawType().equals(JpaRepository.class) && t.getActualTypeArguments()[1].equals(Integer.class)));
    }

    @Test
    public void repositoryFindPersonByEmail_exists() {
        assertDoesNotThrow(() -> PersonRepository.class.getDeclaredMethod("findPersonByEmail", String.class));
    }

    @Test
    public void repositoryFindPersonByEmail_returnsOptionalPerson() {
        try {
            assertEquals(Optional.class, PersonRepository.class.getDeclaredMethod("findPersonByEmail", String.class).getReturnType());
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

}
