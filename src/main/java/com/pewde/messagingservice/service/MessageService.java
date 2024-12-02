package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.exception.*;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.MessageRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.DeleteMessageRequest;
import com.pewde.messagingservice.request.EditMessageRequest;
import com.pewde.messagingservice.request.SendMessageRequest;
import com.pewde.messagingservice.token.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Message getMessageById(int id){
        return messageRepository.findById(id).orElseThrow(MessageDoesNotExistsException::new);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByDialog(int id){
        return messageRepository.findByDialogOrderById(dialogRepository.findById(id).orElseThrow(DialogDoesNotExistsException::new));
    }

    public Message sendMessage(SendMessageRequest request, String token){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
        if (!dialog.getCollocutors().contains(sender)){
            throw new UserDoesNotBelongToDialogException();
        }
        Message message = new Message();
        message.setText(request.getMessage());
        message.setSender(sender);
        message.setDialog(dialog);
        dialog.getMessages().add(message);
        return messageRepository.saveAndFlush(message);
    }

    public ResponseEntity<HttpStatus> deleteMessage(DeleteMessageRequest request, String token){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        Message message = messageRepository.findById(request.getMessageId()).orElseThrow(MessageDoesNotExistsException::new);
        if (message.getSender().getId() != sender.getId()){
            throw new MessageDoesNotBelongToUserException();
        }
        message.setSender(null);
        message.getDialog().getMessages().remove(message);
        sender.getMessages().remove(message);
        messageRepository.delete(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Message editMessage(EditMessageRequest request, String token){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        Message message = messageRepository.findById(request.getMessageId()).orElseThrow(MessageDoesNotExistsException::new);
        if (message.getSender().getId() != sender.getId()){
            throw new MessageDoesNotBelongToUserException();
        }
        message.setText(request.getMessage());
        return messageRepository.saveAndFlush(message);
    }

}
