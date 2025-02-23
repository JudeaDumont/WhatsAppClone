package com.example.whats_app_clone.message;

import com.example.whats_app_clone.chat.Chat;
import com.example.whats_app_clone.chat.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat: " + messageRequest.getChatId() + "not found"));

        Message message = messageMapper.fromMessageRequest(messageRequest, chat);

        messageRepository.save(message);
    }

    public List<MessageResponse> getAllMessages(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse).toList();
    }
}
