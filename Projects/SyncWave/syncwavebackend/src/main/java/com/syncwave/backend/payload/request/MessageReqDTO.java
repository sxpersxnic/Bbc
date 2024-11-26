package com.syncwave.backend.payload.request;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MessageReqDTO {
    private Long chatId;
    private String content;

}
