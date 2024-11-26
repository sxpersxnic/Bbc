package com.fitcom.fitcom_restapi.service;

import com.fitcom.fitcom_restapi.exceptions.FailedValidationException;
import com.fitcom.fitcom_restapi.model.ERole;
import com.fitcom.fitcom_restapi.model.Role;
import com.fitcom.fitcom_restapi.model.User;
import com.fitcom.fitcom_restapi.repository.RoleRepository;
import com.fitcom.fitcom_restapi.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findByUUID(UUID uuid) {
        return userRepository.findByUUID(uuid).orElseThrow(EntityNotFoundException::new);
    }

    public User findByUUIDForUpdate(UUID uuid) {
        return userRepository.findByUUIDForUpdate(uuid).orElseThrow(EntityNotFoundException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User newUser) {
        Role defaultRole = roleRepository.findByName(ERole.ROLE_USER);
        // TODO: String password = newUser.getPassword();
        // TODO: String encodedPassword = passwordEncoder.encode(password);
        // TODO: newUser.setPassword(encodedPassword);
        newUser.setRole(defaultRole);
        return userRepository.save(newUser);

    }

    @Transactional
    public User update(User user, UUID uuid) {
        User existing = findByUUIDForUpdate(uuid);
        mergeUser(existing, user);
        //TODO: String password = existing.getPassword();
        //TODO: String encodedPassword = passwordEncoder.encode(password);
        //TODO: existing.setPassword(encodedPassword);
        return userRepository.save(existing);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public User findByPrincipal(String principal) {
        return userRepository.findByEmail(principal).orElse(userRepository.findByUsername(principal).orElseThrow(EntityNotFoundException::new));
    }

    public void deleteByUUID(UUID uuid) {
        userRepository.deleteByUUID(uuid);
    }

    private void mergeUser(User existing, User changing) {
        Map<String, List<String>> errors = new HashMap<>();

        if (changing.getUsername() != null) {
            if (StringUtils.isNotBlank(changing.getUsername()))  {
                existing.setUsername(changing.getUsername());
            } else {
                errors.put("username", List.of("Username can not be empty!"));
            }
        }
        if (changing.getEmail() != null) {
            if (StringUtils.isNotBlank(changing.getEmail()))  {
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
        if (changing.getRole() != null) {
            if (StringUtils.isNotBlank((changing.getRole().getUuid().toString()))) {
                existing.setRole(changing.getRole());
            } else {
                errors.put("Role", List.of("Role can not be empty!"));
            }
        }
        if (changing.getGarments() != null) {
            existing.setGarments(changing.getGarments());
        }

        if (!errors.isEmpty()) {
            throw new FailedValidationException(errors);
        }
    }
}
