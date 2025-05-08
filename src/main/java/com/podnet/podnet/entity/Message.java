package com.podnet.podnet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    // Скрытый конструктор для Builder
    private Message(Builder builder) {
        this.content = builder.content;
        this.timestamp = builder.timestamp;
        this.author = builder.author;
        this.chat = builder.chat;
    }

    // Статический метод для доступа к Builder
    public static Builder builder() {
        return new Builder();
    }

    // Статический класс Builder
    public static class Builder {
        private String content;
        private LocalDateTime timestamp;
        private User author;
        private Chat chat;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder author(User author) {
            this.author = author;
            return this;
        }

        public Builder chat(Chat chat) {
            this.chat = chat;
            return this;
        }

        public Message build() {
            validate();
            return new Message(this);
        }

        private void validate() {
            if (content == null || content.isBlank()) {
                throw new IllegalArgumentException("Content cannot be empty");
            }
            if (timestamp == null) {
                timestamp = LocalDateTime.now();
            }
            if (author == null) {
                throw new IllegalArgumentException("Author must be specified");
            }
            if (chat == null) {
                throw new IllegalArgumentException("Chat must be specified");
            }
        }
    }
}