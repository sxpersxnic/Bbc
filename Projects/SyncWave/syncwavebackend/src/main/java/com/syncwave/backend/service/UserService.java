package com.syncwave.backend.service;

import com.syncwave.backend.lib.exceptions.FailedValidationException;
import com.syncwave.backend.lib.validation.Validator;
import com.syncwave.backend.model.User;
import com.syncwave.backend.model.enums.ActiveStatus;
import com.syncwave.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(ActiveStatus.OFFLINE);
        return userRepository.save(user);
    }

    public void disconnect(Long id) {
        User u = findById(id);
        u.setStatus(ActiveStatus.OFFLINE);
        userRepository.save(u);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findConnectedUsers() {
        return userRepository.findAllByStatus(ActiveStatus.ONLINE);
    }
    
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public User findByIdForUpdate(Long id) {
        return userRepository.findByIdForUpdate(id).orElseThrow(EntityNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public Set<Long> findTeamIdsByUsername(String username) {
        Set<Long> teamIds = userRepository.findTeamIdsByUsername(username);
        return teamIds;
    }


    public User findByUsername(String username) {
        User foundUser = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        foundUser.getTeams().forEach(System.out::println);
        return foundUser;
    }

    @Transactional
    public User update(User changing, Long id) {
        User existing = this.findByIdForUpdate(id);
        mergePersons(existing, changing);
        return userRepository.save(existing);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void mergePersons(User existing, User changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getUsername() != null) {
            if (StringUtils.isNotBlank(changing.getUsername()) && Validator.isValidUsername(changing.getUsername()) && !existsByUsername(changing.getUsername()) && !changing.getUsername().equals(existing.getUsername())) {
                existing.setUsername(changing.getUsername());
            } else {
                errors.put("username", List.of("Please choose an other username, this one is not valid."));
            }
        }

        if (changing.getEmail() != null) {
            if (StringUtils.isNotBlank(changing.getEmail()) && Validator.isValidEmail(changing.getEmail()) && !existsByEmail(changing.getEmail()) && !changing.getEmail().equals(existing.getEmail())) {
                existing.setEmail(changing.getEmail());
            } else {
                errors.put("email", List.of("Please choose an other email, this one is not valid."));
            }
        }

        if (changing.getPassword() != null) {
            if (StringUtils.isNotBlank(changing.getPassword()) && Validator.isValidPassword(changing.getPassword()) && changing.getPassword().equals(existing.getPassword())) {
                String newPassword = passwordEncoder.encode(changing.getPassword());
                existing.setPassword(newPassword);
            } else {
                errors.put("password", List.of("Password must not be empty."));
            }
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optPerson = userRepository.findByUsername(username);

        if (optPerson.isPresent()) {
            User user = optPerson.get();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), emptyList());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
