package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.request.*;
import com.pewde.messagingservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MessageController", description = "Обмен сообщениями между пользователями")
@Validated
@RestController
@RequestMapping("/api/messaging/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Operation(summary = "Получение сообщения по уникальному идентификатору")
    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable int id){
        return messageService.getMessageById(id);
    }

    @Operation(summary = "Получение списка всех сообщений в системе")
    @GetMapping("/messages")
    public List<Message> getAllMessages(){
        return messageService.getAllMessages();
    }

    @Operation(summary = "Получение всех сообщений в диалоге")
    @GetMapping("/dialog/{id}")
    public List<Message> getAllMessagesByDialog(@PathVariable int id){
        return messageService.getAllMessagesByDialog(id);
    }

    @Operation(summary = "Отправка сообщения")
    @PostMapping("/send")
    public Message sendMessage(@RequestBody @Valid SendMessageRequest request,
                               @RequestHeader("Authorization") String token){
        return messageService.sendMessage(request, token);
    }

    @Operation(summary = "Удаление сообщения")
    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteMessage(@RequestBody DeleteMessageRequest request,
                                                    @RequestHeader("Authorization") String token){
        return messageService.deleteMessage(request, token);
    }

    @Operation(summary = "Редактирование сообщения")
    @PutMapping("/edit")
    public Message editMessage(@RequestBody @Valid EditMessageRequest request,
                               @RequestHeader("Authorization") String token){
        return messageService.editMessage(request, token);
    }

    @Operation(summary = "Ответ на сообщение")
    @PostMapping("/reply")
    public Message replyMessage(@RequestBody @Valid ReplyMessageRequest request,
                                @RequestHeader("Authorization") String token){
        return messageService.replyMessage(request, token);
    }

    @Operation(summary = "Пересылка сообщения")
    @PostMapping("/forward")
    public Message forwardMessage(@RequestBody ForwardMessageRequest request,
                                  @RequestHeader("Authorization") String token){
        return messageService.forwardMessage(request, token);
    }

}
