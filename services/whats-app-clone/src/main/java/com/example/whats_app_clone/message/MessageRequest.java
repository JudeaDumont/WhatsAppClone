package com.example.whats_app_clone.message;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String content;
    private String senderId;
    private String receiverId;
    private MessageType type;
    private String chatId;
}
