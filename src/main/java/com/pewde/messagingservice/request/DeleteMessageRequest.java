package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMessageRequest {

    @Schema(description = "Уникальный идентификатор отправителя сообщения")
    private int senderId;

    @Schema(description = "Уникальный идентификатор сообщения")
    private int messageId;

}
