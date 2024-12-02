package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateConversationRequest {

    @NotNull
    @Schema(description = "Уникальный идентификатор отправителя сообщения")
    private int senderId;

    @NotNull
    @Schema(description = "Список получателей: личные сообщения - 1 получатель, беседа - неограниченное количество получателей", example = "[2]")
    private Set<Integer> receiverIds;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String title;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String message;

}
