package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.request.CreateConversationRequest;
import com.pewde.messagingservice.request.KickOrInviteRequest;
import com.pewde.messagingservice.request.LeaveConversationRequest;
import com.pewde.messagingservice.request.MakeOrUnmakeAdminRequest;
import com.pewde.messagingservice.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(name = "ConversationController", description = "Операции связанные с беседами")
@Validated
@RestController
@RequestMapping("/api/messaging/conversation")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @Operation(summary = "Создание беседы")
    @PostMapping("/create")
    public Dialog createConversation(@RequestBody CreateConversationRequest request,
                                     @RequestHeader("Authorization") String token) {
        return conversationService.createConversation(request, token);
    }

    @Operation(summary = "Выход из беседы")
    @DeleteMapping("/leave")
    public ResponseEntity<HttpStatus> leaveConversation(@RequestBody @Valid LeaveConversationRequest request,
                                                        @RequestHeader("Authorization") String token) {
        return conversationService.leaveConversation(request, token);
    }

    @Operation(summary = "Приглашение пользователя в беседу")
    @PostMapping("/invite")
    public Dialog inviteUser(@RequestBody @Valid KickOrInviteRequest request,
                             @RequestHeader("Authorization") String token){
        return conversationService.inviteUser(request, token);
    }

    @Operation(summary = "Исключение пользователя из беседы")
    @DeleteMapping("/kick")
    public ResponseEntity<HttpStatus> kickUser(@RequestBody @Valid KickOrInviteRequest request,
                                               @RequestHeader("Authorization") String token){
        return conversationService.kickUser(request, token);
    }

    @Operation(summary = "Назначение пользователя администратором")
    @PostMapping("/appoint")
    public Dialog appointAdmin(@RequestBody @Valid MakeOrUnmakeAdminRequest request,
                               @RequestHeader("Authorization") String token) {
        return conversationService.appointAdmin(request, token);
    }

    @Operation(summary = "Исключение пользователя с должности администратора")
    @DeleteMapping("/demote")
    public Dialog demoteAdmin(@RequestBody @Valid MakeOrUnmakeAdminRequest request,
                              @RequestHeader("Authorization") String token) {
        return conversationService.demoteAdmin(request, token);
    }

}
