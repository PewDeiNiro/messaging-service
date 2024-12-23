package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @Schema(description = "Уникальный идентификатор отправителя сообщения")
    private int userId;

    @Schema(description = "Уникальный идентификатор диалога")
    private int dialogId;

    @NotEmpty
    @Schema(description = "Сообщение, которое пользователь хочет отправить", example = "Всем приветъ!!!!")
    private String message;


}
