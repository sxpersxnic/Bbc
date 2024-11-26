package com.syncwave.backend.payload.mapper;

import com.syncwave.backend.model.Chat;
import com.syncwave.backend.model.Team;
import com.syncwave.backend.model.User;
import com.syncwave.backend.payload.request.ChatReqDTO;
import com.syncwave.backend.payload.response.ChatResDTO;

public class ChatMapper {

    public static Chat fromDTO(ChatReqDTO reqDTO) {
        Chat chat = new Chat();
        chat.setUsers(reqDTO.getUserIds().stream().map(User::new).toList());
        chat.setName(reqDTO.getName());
        chat.setGroup(reqDTO.getIsGroup());
        if (reqDTO.getTeamId() != null) {
            chat.setTeam(new Team(reqDTO.getTeamId()));
        }
        return chat;
    }

    public static ChatResDTO toDTO(Chat chat, User user) {
        ChatResDTO resDTO = new ChatResDTO();
        resDTO.setId(chat.getId());
        resDTO.setUserIds(chat.getUsers().stream().map(User::getId).toList());
        if (chat.getIsGroup()) {
            resDTO.setName(chat.getName());
        } else {
            User otherUser = chat.getUsers().stream().filter(u -> !u.equals(user)).findFirst().orElse(null);
            assert otherUser != null;
            resDTO.setName(otherUser.getUsername());
        }
        if (chat.getTeam() != null) {
            resDTO.setTeamId(chat.getTeam().getId());
        }
        resDTO.setGroup(chat.getIsGroup());
        return resDTO;
    }
}
