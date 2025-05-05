package com.podnet.podnet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Message {
    @Id
    private Long id;

    private String content;
    private LocalDateTime timestamp;
    @ManyToOne
    private User author;
    @ManyToOne
    private Chat chat;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
