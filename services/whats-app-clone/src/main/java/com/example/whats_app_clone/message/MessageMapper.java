package com.example.whats_app_clone.message;

import com.example.whats_app_clone.chat.Chat;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public Message fromMessageRequest(MessageRequest messageRequest, Chat chat){
        return Message.builder()
                .content(messageRequest.getContent())
                .chat(chat)
                .senderID(messageRequest.getSenderId())
                .recipientID(messageRequest.getReceiverId())
                .type(messageRequest.getType())
                .state(MessageState.SENT)
                .build();
    }
}

