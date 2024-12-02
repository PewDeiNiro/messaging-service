package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveConversationRequest {

    @NotNull
    @Schema(description = "Уникальный идентификатор пользователя")
    private int userId;

    @NotNull
    @Schema(description = "Уникальный идентификатор беседы")
    private int dialogId;

}
