package com.example.whats_app_clone.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
    public ChatResponse toChatResponse(Chat chat, String userId){
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(userId))
                .unreadCount(chat.getUnreadMessages(userId))
                .lastMessage(chat.getLastMessageId())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .build();
    }
}
