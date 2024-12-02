package com.pewde.messagingservice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeOrUnmakeAdminRequest {

    @NotNull
    @Schema(description = "Уникальный идентификатор создателя беседы")
    private int creatorId;

    @NotNull
    @Schema(description = "Уникальный идентификатор нового администратора")
    private int userId;

    @NotNull
    @Schema(description = "Уникальный идентификатор диалога")
    private int dialogId;

}
