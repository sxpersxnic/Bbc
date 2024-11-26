package ch.bbcag.ticketshop.person;

import ch.bbcag.ticketshop.lib.exceptions.FailedValidationException;
import ch.bbcag.ticketshop.role.Role;
import ch.bbcag.ticketshop.role.RoleRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findById(Integer id) {
        return personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person update(Person person, Integer id) {
        Person existing = this.findById(id);
        mergePerson(existing, person);
        String password = existing.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        existing.setPassword(encodedPassword);
        return personRepository.save(existing);
    }

    public Person findByEmail(String email) {
        return personRepository.findPersonByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public Person create(Person newPerson) {
        Role defaultRole = roleRepository.findByName("USER");
        String password = newPerson.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        newPerson.setPassword(encodedPassword);
        newPerson.getAssignedRoles().add(defaultRole);
        return personRepository.save(newPerson);
    }
    public void deleteById(Integer id) {
        personRepository.deleteById(id);
    }
    private void mergePerson(Person existing, Person changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getEmail() != null) {
            if (StringUtils.isNotBlank(changing.getEmail())) {
                existing.setEmail(changing.getEmail());
            } else {
                errors.put("email", List.of("Email can not be empty!"));
            }
        }
        if (changing.getPassword() != null) {
            if (StringUtils.isNotBlank(changing.getPassword())) {
                existing.setPassword(changing.getPassword());
            } else {
                errors.put("password", List.of("Password can not be empty!"));
            }
        }
        if (changing.getEvents() != null) {
            existing.setEvents(changing.getEvents());
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }
}
