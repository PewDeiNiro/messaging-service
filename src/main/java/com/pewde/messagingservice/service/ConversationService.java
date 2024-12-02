package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.exception.UserDoesNotExistsException;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.CreateConversationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DialogRepository dialogRepository;

    public Dialog createConversation(CreateConversationRequest request, String token){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        List<User> receivers = new ArrayList<>(List.of(sender));
        for (int id : request.getReceiverIds()){
            if (sender.getId() != id) {
                receivers.add(userRepository.findById(id).orElseThrow(UserDoesNotExistsException::new));
            }
        }
        List<User> collocutors = new ArrayList<>(List.copyOf(receivers));
        List<Message> messages = new ArrayList<>();
        Dialog dialog = new Dialog();

        dialog.setCollocutors(collocutors);
        if (request.getTitle() == null || request.getTitle().isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (User user : collocutors){
                builder.append(user.getUsername()).append(" ");
            }
            dialog.setTitle(builder.toString().trim());
        }
        else{
            dialog.setTitle(request.getTitle());
        }
        if (request.getMessage() != null && !request.getMessage().isEmpty()){
            Message message = new Message();
            message.setText(request.getMessage());
            message.setSender(sender);
            message.setDialog(dialog);
            messages.add(message);
        }
        dialog.setMessages(messages);
        return dialogRepository.saveAndFlush(dialog);
    }

}
