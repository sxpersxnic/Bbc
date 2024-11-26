package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Message;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.MessageReqDTO;
import com.syncwave.backend.payload.response.MessageResDTO;
import com.syncwave.backend.repository.ChatRepository;

import java.sql.Timestamp;
import java.time.Instant;

public class MessageMapper {

    public static MessageResDTO toDTO(Message message) {
        MessageResDTO dto = new MessageResDTO();

        dto.setId(message.getId());
        dto.setChatId(message.getChat().getId());
        dto.setSenderId(message.getSender().getId());
        dto.setContent(message.getContent());
        dto.setSendTime(message.getSendTime());

        return dto;
    }

    public static Message fromDTO(MessageReqDTO dto, User sender, ChatRepository chatRepository) {
        Message message = new Message();
        System.out.println("FROMDTO: " + message.getId());
        message.setSender(sender);
        message.setContent(dto.getContent());
        message.setSendTime(Timestamp.from(Instant.now()));
        if (chatRepository.findById(dto.getChatId()).isPresent()) {
            Chat chat = chatRepository.findById(dto.getChatId()).get();
            message.setChat(chat);
        }


        return message;
    }
}
