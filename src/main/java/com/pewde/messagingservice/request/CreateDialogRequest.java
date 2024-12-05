package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDialogRequest {

    @Schema(description = "Уникальный идентификатор отправителя сообщения")
    private int userId;

    @Schema(description = "Уникальный идентификатор получателя сообщения")
    private int receiverId;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Сообщение, которое будет отправлено при создании диалога")
    private String message;

}
