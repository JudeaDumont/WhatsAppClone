package com.example.whats_app_clone.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMessage(@RequestBody MessageRequest messageRequest) {
        messageService.saveMessage(messageRequest);
    }

    @PostMapping(value = "/upload-media", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadMedia(@RequestParam("chat-id") String chatId,
                            @RequestParam("file") MultipartFile file,
                            Authentication authentication) {
        messageService.uploadMediaMessage(chatId, file, authentication);
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setMessagesToBeSeen(@RequestParam("chat-id") String chatId, Authentication authentication){
        messageService.setMessagesToBeSeen(chatId, authentication);
    }

    @GetMapping("/chat/{chat-id}")
    public ResponseEntity<List<MessageResponse>> getMessage(@PathVariable String chatId, Authentication authentication) {
        return ResponseEntity.ok(messageService.findChatMessage(chatId));
    }
}
