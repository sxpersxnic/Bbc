package com.syncwave.backend.service;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.response.ChatResDTO;
import com.syncwave.backend.repository.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }


    public Chat createChat(Chat chat) {

        return chatRepository.save(chat);
    }

    public List<ChatResDTO> findByUserIsPart(User user) {
        List<Chat> chats = chatRepository.findByUsersContains(user);
        List<ChatResDTO> chatResDTOs = new ArrayList<>();
        chats.forEach(chat -> {
            ChatResDTO resDTO = new ChatResDTO();
            resDTO.setId(chat.getId());
            if (!chat.getIsGroup()) {
                User otherUser = chat.getUsers().stream().filter(u -> !u.equals(user)).findFirst().orElse(null);
                assert otherUser != null;
                resDTO.setName(otherUser.getUsername());
            } else {
                resDTO.setName(chat.getName());
            }
            resDTO.setGroup(chat.getIsGroup());
            resDTO.setUserIds(chat.getUsers().stream().map(User::getId).toList());
            if (chat.getTeam() == null) {
                chatResDTOs.add(resDTO);
            }
        });
        return chatResDTOs;
    }

    public Chat findById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            return chat.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

}
