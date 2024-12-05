package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForwardMessageRequest {

    @Schema(description = "Уникальный идентификатор пользователя")
    private int userId;

    @Schema(description = "Уникальный идентификатор сообщения")
    private int messageId;

    @Schema(description = "Уникальный идентификатор диалога, куда будет переслано сообщение")
    private int dialogId;

    @Schema(description = "Сообщение, которое будет отправлено в ответе")
    private String text;

}
