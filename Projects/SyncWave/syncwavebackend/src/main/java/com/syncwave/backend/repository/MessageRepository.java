package com.syncwave.backend.repository;

import com.syncwave.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByContent(String content);
    List<Message> findByChatId(Long chatId);
}
