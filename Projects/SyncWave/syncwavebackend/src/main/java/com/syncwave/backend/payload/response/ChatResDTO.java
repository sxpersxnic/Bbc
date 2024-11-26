package com.syncwave.backend.payload.response;

import com.syncwave.backend.payload.request.ChatReqDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChatResDTO extends ChatReqDTO {

    public Long id;
}
