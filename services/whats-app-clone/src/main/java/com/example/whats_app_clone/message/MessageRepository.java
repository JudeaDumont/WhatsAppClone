package com.example.whats_app_clone.message;

import com.example.whats_app_clone.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query(name = MessageConstants.FIND_MESSAGES_BY_CHAT_ID)
    Collection<Message> findMessagesByChatId(String chatId);

    @Query(name = MessageConstants.SET_MESSAGES_TO_BE_SEEN_BY_CHAT)
    @Modifying
    void setMessagesToBeSeenByChatId(@Param("chatId") Chat chat, @Param("newState") MessageState messageState);
}
