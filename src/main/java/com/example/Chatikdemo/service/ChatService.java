package com.example.Chatikdemo.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public String answerMessage(String data){
        StringBuilder result = new StringBuilder();
        for (int i=0;i<data.length();i++){
            int index =ThreadLocalRandom.current().nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }
}
