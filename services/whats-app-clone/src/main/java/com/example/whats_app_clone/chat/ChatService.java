package com.example.whats_app_clone.chat;

import com.example.whats_app_clone.user.User;
import com.example.whats_app_clone.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByReceiverId(Authentication currentUser) {
        final String userId = currentUser.getName();
        return chatRepository.findChatsBySenderId(userId).stream()
                .map((c) -> chatMapper.toChatResponse(c, userId)).toList();

    }

    public String createChat(String senderId, String recipientId) {
        Optional<Chat> existingChat = chatRepository.findChatsByRecipientAndSenderId(senderId, recipientId);
        if(existingChat.isPresent()){
            return existingChat.get().getId();
        }
        User sender = userRepository.findPublicById(senderId).orElseThrow(()-> new EntityNotFoundException("User with ID: "+ senderId +" not found"));
        User recipient = userRepository.findPublicById(senderId).orElseThrow(()-> new EntityNotFoundException("User with ID: "+ recipientId +" not found"));

        Chat newChat = new Chat();
        newChat.setSender(sender);
        newChat.setRecipient(recipient);

        Chat savedChat = chatRepository.save(newChat);
        return savedChat.getId();
    }
}
