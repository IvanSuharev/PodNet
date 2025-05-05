package com.podnet.podnet.controller;

import com.podnet.podnet.entity.Message;
import com.podnet.podnet.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class MessageController {

    @Autowired // Добавлено внедрение зависимости
    private MessageRepository messageRepo;

    @MessageMapping("/chat.send")
    @SendToUser("/topic/message/{userId}")
    public Message sendMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.now());
        message = messageRepo.save(message);

        // Обновленный хедер для доставки сообщения
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
                .create(SimpMessageType.MESSAGE);
        headerAccessor.setDestination("/topic/message/" + message.getAuthor().getId());
        return message;
    }

//    @GetMapping("/message/{senderId}/{receiverId}")
//    public ResponseEntity<List<Message>> getChatHistory(
//            @PathVariable Long senderId,
//            @PathVariable Long receiverId) {
//        List<Message> messages = messageRepo.findBySenderIdAndReceiverId(senderId, receiverId);
//        return ResponseEntity.ok(messages);
//    }
//    @PostMapping("/message")
//    public ResponseEntity<Message> addChatMessage(@Payload Message message) {
//        message.setTimestamp(LocalDateTime.now());
//        messageRepo.save(message);
//        return ResponseEntity.ok(message);
//    }
}