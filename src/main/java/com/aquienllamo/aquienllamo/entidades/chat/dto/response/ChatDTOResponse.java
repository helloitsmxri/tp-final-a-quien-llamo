package com.aquienllamo.aquienllamo.entidades.chat.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatDTOResponse {
    private String uuidChat;
    private LocalDateTime fechaChat;

}
