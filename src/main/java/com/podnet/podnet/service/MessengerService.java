package com.podnet.podnet.service;

import com.podnet.podnet.dto.MessageDto;
import com.podnet.podnet.entity.Chat;
import com.podnet.podnet.entity.Message;
import com.podnet.podnet.entity.User;
import com.podnet.podnet.repository.ChatRepository;
import com.podnet.podnet.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Builder
@Slf4j
public class MessengerService {
    private final UserServiceImpl userServiceImpl;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public MessageDto putMessage(Long chatId, String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Текст сообщения не может быть пустым");
        }

        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Чат не найден: " + chatId));

        User user = userServiceImpl.getCurrentUser()
                .orElseThrow(() -> new SecurityException("Пользователь не аутентифицирован"));

        Message message = Message.builder()
                .content(text)
                .author(user)
                .chat(chat)
                .timestamp(LocalDateTime.now())
                .build();

        messageRepository.save(message);
        log.info("Сообщение сохранено: {}", message.getId());

        return mapToDto(message);
    }

    private MessageDto mapToDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .authorId(message.getAuthor().getId())
                .chatId(message.getChat().getId())
                .build();
    }

}