package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditMessageRequest {

    @NotNull
    @Schema(description = "Уникальный идентификатор отправителя")
    private int senderId;

    @NotNull
    @Schema(description = "Уникальный идентификатор сообщения")
    private int messageId;

    @NotEmpty
    @Schema(description = "Новый текст сообщения")
    private String message;

}
