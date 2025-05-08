package com.podnet.podnet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long authorId;
    private Long chatId;
}
