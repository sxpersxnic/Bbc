package ch.bbcag.backend.todolist.person;

import ch.bbcag.backend.todolist.FailedValidationException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person create(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Person update(Person changing, Integer id) {
        Person existing = this.findById(id);
        mergePersons(existing, changing);
        return personRepository.save(existing);
    }

    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }

    private void mergePersons(Person existing, Person changing) {

        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getUsername() != null) {
            if (StringUtils.isNotBlank(changing.getUsername())) {
                existing.setUsername(changing.getUsername());
            } else {
                errors.put("username", List.of("username must not be empty"));
            }
        }

        if (changing.getPassword() != null) {
            String newPassword = passwordEncoder.encode(changing.getPassword());
            existing.setPassword(newPassword);
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }
}