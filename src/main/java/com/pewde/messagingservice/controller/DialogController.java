package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.request.CreateDialogRequest;
import com.pewde.messagingservice.service.DialogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DialogController", description = "Операции связанные с диалогами")
@Validated
@RestController
@RequestMapping("/api/messaging/dialog")
public class DialogController {

    @Autowired
    private DialogService dialogService;

    @Operation(summary = "Получение диалога по уникальному идентификатору")
    @GetMapping("/{id}")
    public Dialog getDialogById(@PathVariable int id){
        return dialogService.getDialogById(id);
    }

    @Operation(summary = "Получение списка всех диалогов в системе")
    @GetMapping("/dialogs")
    public List<Dialog> getAllDialogs(){
        return dialogService.getAllDialogs();
    }

    @Operation(summary = "Получение последнего сообщения в диалоге")
    @GetMapping("/last/{id}")
    public Message getLastMessageInDialog(@PathVariable int id){
        return dialogService.getLastMessageInDialog(id);
    }

    @Operation(summary = "Получение списка всех новых сообщений по ID последнего прочитанного")
    @GetMapping("/last")
    public List<Message> getMessageAfterIdInDialog(@RequestParam @NotNull int dialogId, @RequestParam @NotNull int messageId){
        return dialogService.getMessageAfterIdInDialog(dialogId, messageId);
    }

    @Operation(summary = "Создание диалога")
    @PostMapping("/create")
    public Dialog createDialog(@RequestBody @Valid CreateDialogRequest request,
                               @RequestHeader("Authorization") String token){
        return dialogService.createDialog(request, token);
    }

}
