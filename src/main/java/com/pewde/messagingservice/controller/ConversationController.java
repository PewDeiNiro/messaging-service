package com.pewde.messagingservice.controller;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.request.CreateConversationRequest;
import com.pewde.messagingservice.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Dialog createConversation(@RequestBody @Valid CreateConversationRequest request,
                                     @RequestHeader("Authorization") String token) {
        return conversationService.createConversation(request, token);
    }

}
