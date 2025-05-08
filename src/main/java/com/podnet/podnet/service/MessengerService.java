package com.podnet.podnet.service;

import com.podnet.podnet.dto.MessageDto;
import com.podnet.podnet.entity.Chat;
import com.podnet.podnet.entity.Message;
import com.podnet.podnet.entity.User;
import com.podnet.podnet.repository.ChatRepository;
import com.podnet.podnet.repository.MessageRepository;
import com.podnet.podnet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class MessengerService {
    private final UserServiceImpl userServiceImpl;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Chat createGroupChat(String chatName, Set<Long> memberIds) {
        User creator = userServiceImpl.getCurrentUser()
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<User> members = userRepository.findAllById(memberIds);
        members.add(creator); // Добавляем создателя в участники

        return chatRepository.save(
                Chat.builder()
                        .name(chatName)
                        .users(members)
                        .build()
        );
    }

    public MessageDto putMessage(Long chatId, String text) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found with id: " + chatId));

        User user = userServiceImpl.getCurrentUser()
                .orElseThrow(() -> new SecurityException("User not authenticated"));

        Message message = createMessage(text, user, chat);
        messageRepository.save(message);

        return mapToDto(message);
    }

    private Message createMessage(String text, User user, Chat chat) {
        return Message.builder()
                .content(text)
                .author(user)
                .timestamp(LocalDateTime.now())
                .chat(chat)
                .build();
    }
    private MessageDto mapToDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getContent(),
                message.getTimestamp(),
                message.getAuthor().getId(),
                message.getChat().getId()
        );
    }
}