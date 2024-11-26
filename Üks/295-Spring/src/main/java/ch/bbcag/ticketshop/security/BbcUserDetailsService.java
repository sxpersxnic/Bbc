package ch.bbcag.ticketshop.security;


import ch.bbcag.ticketshop.person.Person;
import ch.bbcag.ticketshop.person.PersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Is used by AuthenticationManager for authenticate method
 */
@Service
public class BbcUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public BbcUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> optPerson = personRepository.findPersonByEmail(email);

        if (optPerson.isPresent()) {
            Person person = optPerson.get();
            return new User(person.getEmail(), person.getPassword(), person.getAssignedRoles());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

}
