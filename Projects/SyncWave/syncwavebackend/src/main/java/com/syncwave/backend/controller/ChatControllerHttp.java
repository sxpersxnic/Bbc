package com.syncwave.backend.controller;

import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.ChatMapper;
import com.syncwave.backend.repository.UserRepository;
import com.syncwave.backend.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static com.syncwave.backend.lib.constants.ControllerConstants.CHAT;
import static com.syncwave.backend.lib.constants.ControllerConstants.CHATCONTROLLER_PATH;

@RestController
@RequestMapping(CHATCONTROLLER_PATH)
public class ChatControllerHttp {

    private final ChatService chatService;
    private final UserRepository userRepository;

    public ChatControllerHttp(ChatService chatService, UserRepository userRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @GetMapping(CHAT)
    public ResponseEntity<?> getChats() {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.status(HttpStatus.OK).body(chatService.findByUserIsPart(user));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't get Chats");
        }
    }

    @GetMapping(CHAT + "/{id}")
    public ResponseEntity<?> getChatById(@PathVariable Long id) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            return ResponseEntity.status(HttpStatus.OK).body(ChatMapper.toDTO(chatService.findById(id), user));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat not found");
        }
    }

}
