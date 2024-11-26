package com.syncwave.backend.controller;

import com.syncwave.backend.lib.exceptions.ChatNotFoundException;
import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Message;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.ChatMapper;
import com.syncwave.backend.payload.mapper.MessageMapper;
import com.syncwave.backend.payload.request.ChatReqDTO;
import com.syncwave.backend.payload.request.MessageReqDTO;
import com.syncwave.backend.repository.ChatRepository;
import com.syncwave.backend.repository.MessageRepository;
import com.syncwave.backend.repository.UserRepository;
import com.syncwave.backend.service.ChatService;
import com.syncwave.backend.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.syncwave.backend.lib.constants.ControllerConstants.*;

@Controller
@RequestMapping(WS_ENDPOINT)
public class ChatController {

    private final MessageService messageService;
    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ChatController(MessageService messageService, ChatService chatService, UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageRepository) {
        this.messageService = messageService;
        this.chatService = chatService;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @GetMapping(MSGS_DST + "/{chatId}")
    public ResponseEntity<?> findChatMessages(@PathVariable Long chatId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.findChatMessages(chatId));
        } catch (ChatNotFoundException e) {
            Map<String, List<String>> error = new HashMap<>();
            error.put("Chat", List.of("Error: Chat could not be found"));
            throw new ChatNotFoundException(error);
        }
    }

    @PostMapping(ADD_USR_PRX)
    public ResponseEntity<?> createChat(@RequestBody ChatReqDTO reqDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(ChatMapper.fromDTO(reqDTO)));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't create Chat");
        }
    }

    @PostMapping(SEND_MSG_PRX)
    public ResponseEntity<?> sendMessage(@RequestBody MessageReqDTO reqDTO) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            Message message = MessageMapper.fromDTO(reqDTO, user, chatRepository);
            Chat chat = message.getChat();

            if (chat.getUsers().contains(user)) {
                return ResponseEntity.status(HttpStatus.CREATED).body(MessageMapper.toDTO(messageService.save(message)));
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not part of Chat");
            }


        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't send message");
        }
    }

    @PutMapping(EDIT_MSG_PRX + "/{id}")
    public ResponseEntity<?> updateMessage(@PathVariable Long id, @RequestBody MessageReqDTO reqDTO) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            Message message = MessageMapper.fromDTO(reqDTO, user, chatRepository);
            Chat chat = message.getChat();

            if (chat.getUsers().contains(user) && message.getSender().equals(user)) {
                return ResponseEntity.status(HttpStatus.OK).body(MessageMapper.toDTO(messageService.update(id, message)));
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User is either not part of the chat or not the owner of the message");
            }

        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Couldn't update message");
        }
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

    @DeleteMapping(DELETE_MSG_PRX + "/{id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Long id) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByUsername(userName).orElseThrow(EntityNotFoundException::new);
            Message message = messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            if (user.equals(message.getSender())) {
                messageService.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner of the message");
            }

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found");
        }
    }
}

