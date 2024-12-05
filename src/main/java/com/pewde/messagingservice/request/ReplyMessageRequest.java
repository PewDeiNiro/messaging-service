package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyMessageRequest {

    @Schema(description = "Уникальный идентификатор пользователя")
    private int userId;

    @Schema(description = "Уникальный идентификатор сообщения")
    private int messageId;

    @NotEmpty
    @Schema(description = "Сообщение, которое будет отправлено в ответе")
    private String text;

}
