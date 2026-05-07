package com.aquienllamo.aquienllamo.model.dtos.Response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatDTOResponse {
    private String uuidChat;
    private LocalDateTime fechaChat;

}
