package com.syncwave.backend.repository;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByUsersContains(User user);
}
