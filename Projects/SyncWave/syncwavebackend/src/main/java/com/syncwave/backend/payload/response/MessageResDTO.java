package com.syncwave.backend.payload.response;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MessageResDTO {
    private Long id;
    private Long senderId;
    private Long chatId;
    private String content;
    private Timestamp sendTime;
}
