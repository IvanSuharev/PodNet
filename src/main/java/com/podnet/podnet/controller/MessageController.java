package com.podnet.podnet.controller;

import com.podnet.podnet.dto.MessageDto;
import com.podnet.podnet.entity.Chat;
import com.podnet.podnet.entity.Message;
import com.podnet.podnet.entity.User;
import com.podnet.podnet.repository.ChatRepository;
import com.podnet.podnet.repository.MessageRepository;
import com.podnet.podnet.repository.UserRepository;
import com.podnet.podnet.service.MessengerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private ChatRepository chatRepo;

    @Autowired
    private UserRepository userRepo;

    @MessageMapping("/chat.send")
    @SendToUser("/topic/message/{userId}")
    public Message sendMessage(@Payload MessageDto messageDto) {
        // Получаем сущности из БД
        User author = userRepo.findById(messageDto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Chat chat = chatRepo.findById(messageDto.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        // Создаем сообщение
        Message message = new Message();
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setAuthor(author);
        message.setChat(chat);

        return messageRepo.save(message);
    }

    @GetMapping("/chat/{chatId}/messages")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable Long chatId) {
        List<Message> messages = messageRepo.findByChatId(chatId);
        return ResponseEntity.ok(messages);
    }
    @RestControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({
                EntityNotFoundException.class,
                SecurityException.class,
                IllegalArgumentException.class
        })
        public ResponseEntity<String> handleBadRequestExceptions(Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
