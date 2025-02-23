package com.example.whats_app_clone.message;

import com.example.whats_app_clone.chat.Chat;
import com.example.whats_app_clone.chat.ChatRepository;
import com.example.whats_app_clone.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;

    public void saveMessage(MessageRequest messageRequest) {
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat: " + messageRequest.getChatId() + "not found"));

        Message message = messageMapper.fromMessageRequest(messageRequest, chat);

        messageRepository.save(message);
    }

    public List<MessageResponse> findChatMessage(String chatId) {
        return messageRepository.findMessagesByChatId(chatId)
                .stream()
                .map(messageMapper::toMessageResponse).toList();
    }

    @Transactional
    public void setMessagesToBeSeen(String chatId, Authentication authentication){
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat: " + chatId + "not found"));

        final String recipient = getRecipientId(chat, authentication);

        messageRepository.setMessagesToBeSeenByChatId(chat, MessageState.SEEN);

        //todo: notification
    }

    public void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication){
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new EntityNotFoundException("Chat: " + chatId + "not found"));
        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(file, senderId);

        Message message = messageMapper.fromMultiMediaMesssage(chat, senderId, recipientId, MessageType.IMAGE, MessageState.SENT, filePath);
        messageRepository.save(message);
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if(chat.getSender().getId().equals(authentication.getName())){
            return chat.getSender().getId();
        }
        return chat.getRecipient().getId();
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        if(chat.getSender().getId().equals(authentication.getName())){
            return chat.getRecipient().getId();
        }
        return chat.getSender().getId();
    }
}
