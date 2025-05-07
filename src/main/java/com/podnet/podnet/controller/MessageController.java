package com.podnet.podnet.controller;

import com.podnet.podnet.entity.Message;
import com.podnet.podnet.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

    @Autowired // Добавлено внедрение зависимости
    private MessageRepository messageRepo;

    @MessageMapping("/chat.send")
    @SendToUser("/topic/message/{authorId}")
    public Message sendMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @GetMapping("/chat/{chatId}/messages")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageRepo.findByChatId(chatId));
    }

    // Создание нового сообщения через REST
    @PostMapping("/message")
    public ResponseEntity<Message> createMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.now());
        Message savedMessage = messageRepo.save(message);
        return ResponseEntity.ok(savedMessage);
    }
}