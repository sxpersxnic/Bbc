package ch.bbcag.ticketshop.security;

import ch.bbcag.ticketshop.person.Person;

public class AuthMapper {

    public static Person toPerson(AuthRequestDTO source) {
        Person person = new Person();
        person.setEmail(source.getEmail());
        person.setPassword(source.getPassword());
        return person;
    }

    public static SignUpResponseDTO toSignUpResponseDTO(Person source) {
        return new SignUpResponseDTO(source.getId(), source.getEmail());
    }

}
