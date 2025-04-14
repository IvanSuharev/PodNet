package com.example.Chatikdemo.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.Chatikdemo.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topics/messages")
    public String processMessageFromClient(String message){
        return "{\"response\" : \"" + chatService.answerMessage(message) + "\"}";
    }
}
