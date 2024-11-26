package com.syncwave.backend.service;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Message;
import com.syncwave.backend.model.Notification;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.mapper.MessageMapper;
import com.syncwave.backend.payload.request.MessageReqDTO;
import com.syncwave.backend.payload.response.MessageResDTO;
import com.syncwave.backend.repository.ChatRepository;
import com.syncwave.backend.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static com.syncwave.backend.lib.constants.ControllerConstants.QUEUE_DST;

@Service
public class MessageService {

    private final SimpMessagingTemplate template;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public MessageService(SimpMessagingTemplate template, MessageRepository messageRepository, ChatRepository chatRepository) {
        this.template = template;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    public MessageResDTO processMessage(MessageReqDTO dto, Chat chat, User sender) {
        Message requestedMsg = MessageMapper.fromDTO(dto, sender, chatRepository);
        Message savedMsg = save(requestedMsg);
        for (User user : chat.getUsers()) {
            template.convertAndSendToUser(user.getUsername(), QUEUE_DST, new Notification(savedMsg.getId(), savedMsg.getSender().getId(), user.getId(), savedMsg.getContent(), Instant.now()));
        }
        return MessageMapper.toDTO(savedMsg);
    }

    public List<MessageResDTO> findChatMessages(Long chatId) {
        List<Message> messages = findMessagesByChatId(chatId);
        return messages.stream().map(MessageMapper::toDTO).toList();
    }


    @Transactional
    public Message save(Message message) {
        System.out.println(message.getId());
        Chat chat = message.getChat();
        Message messageFromDB = messageRepository.save(message);
        template.convertAndSend("/chat/" + chat.getId() + "/queue", MessageMapper.toDTO(messageFromDB));
        return messageFromDB;
    }

    public Message update(Long id, Message newMessage) {
        Message oldMessage = messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        oldMessage.setContent(newMessage.getContent());
        Chat chat = newMessage.getChat();
        Message messageFromDB = messageRepository.save(oldMessage);
        template.convertAndSend("/chat/" + chat.getId() + "/queue/update", MessageMapper.toDTO(messageFromDB));
        return messageFromDB;
    }


    public List<Message> findMessagesByChatId(Long chatId) {
        return messageRepository.findByChatId(chatId);
    }

    public void deleteById(Long id) {
        Message messageFromDB = messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Chat chat = messageFromDB.getChat();
        template.convertAndSend("/chat/" + chat.getId() + "/queue/delete", MessageMapper.toDTO(messageFromDB));
        messageRepository.deleteById(id);
    }
}
