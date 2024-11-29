package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.request.CreateDialogRequest;
import com.pewde.messagingservice.request.DeleteMessageRequest;
import com.pewde.messagingservice.request.SendMessageRequest;
import com.pewde.messagingservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MessageController", description = "Обмен сообщениями между пользователями")
@Validated
@RestController
@RequestMapping("/api/messaging")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Получение диалога по уникальному идентификатору")
    @GetMapping("/dialog/{id}")
    public Dialog getDialogById(@PathVariable int id){
        return messageService.getDialogById(id);
    }

    @Operation(summary = "Получение списка всех диалогов в системе")
    @GetMapping("/dialog/dialogs")
    public List<Dialog> getAllDialogs(){
        return messageService.getAllDialogs();
    }

    @Operation(summary = "Получение сообщения по уникальному идентификатору")
    @GetMapping("/message/{id}")
    public Message getMessageById(@PathVariable int id){
        return messageService.getMessageById(id);
    }

    @Operation(summary = "Получение списка всех сообщений в системе")
    @GetMapping("/message/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @Operation(summary = "Получение всех сообщений в диалоге")
    @GetMapping("/message/dialog/{id}")
    public List<Message> getAllMessagesByDialog(@PathVariable int id){
        return messageService.getAllMessagesByDialog(id);
    }

    @Operation(summary = "Получение последнего сообщения в диалоге")
    @GetMapping("/message/dialog/last/{id}")
    public Message getLastMessageInDialog(@PathVariable int id){
        return messageService.getLastMessageInDialog(id);
    }

    @Operation(summary = "Получение списка всех новых сообщений по ID последнего прочитанного")
    @GetMapping("/message/dialog/last")
    public List<Message> getMessageAfterIdInDialog(@RequestParam @NotNull int dialogId, @RequestParam @NotNull int messageId){
        return messageService.getMessageAfterIdInDialog(dialogId, messageId);
    }

    @Operation(summary = "Создание диалога")
    @PostMapping("/dialog/create")
    public Dialog createDialog(@RequestBody @Valid CreateDialogRequest request,
                               @RequestHeader("Authorization") String token){
        return messageService.createDialog(request, token);
    }

    @Operation(summary = "Отправка сообщения")
    @PostMapping("/message/send")
    public Message sendMessage(@RequestBody @Valid SendMessageRequest request,
                               @RequestHeader("Authorization") String token){
        return messageService.sendMessage(request, token);
    }

    @Operation(summary = "Удаление сообщения")
    @DeleteMapping("/message/delete")
    public ResponseEntity<HttpStatus> deleteMessage(@RequestBody @Valid DeleteMessageRequest request,
                                                    @RequestHeader("Authorization") String token){
        return messageService.deleteMessage(request, token);
    }

}
