package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.enums.DialogType;
import com.pewde.messagingservice.enums.MessageType;
import com.pewde.messagingservice.exception.*;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.MessageRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.*;
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
        if (dialog.getType().equals(DialogType.DIALOG) && dialog.getCollocutors().get(0).getBlocklist().contains(sender)){
            throw new UserBlockedThisException();
        }
        if (!dialog.getCollocutors().contains(sender)){
            throw new UserDoesNotBelongToDialogException();
        }
        Message message = new Message();
        message.setText(request.getMessage());
        message.setSender(sender);
        message.setDialog(dialog);
        message.setType(MessageType.MESSAGE);
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
        message.setParent(null);
        for (Message reply : message.getReplies()){
            reply.setParent(null);
        }
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

    public Message replyMessage(ReplyMessageRequest request, String token){
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(user, token);
        Message parent = messageRepository.findById(request.getMessageId()).orElseThrow(MessageDoesNotExistsException::new),
                message = new Message();
        message.setSender(user);
        message.setParent(parent);
        message.setType(MessageType.REPLY);
        message.setText(request.getText());
        message.setDialog(parent.getDialog());
        return messageRepository.saveAndFlush(message);
    }

    public Message forwardMessage(ForwardMessageRequest request, String token){
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(user, token);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
        Message parent = messageRepository.findById(request.getMessageId()).orElseThrow(MessageDoesNotExistsException::new),
                message = new Message();
        if (!parent.getDialog().getCollocutors().contains(user)){
            throw new UserDoesNotBelongToDialogException();
        }
        message.setSender(user);
        message.setParent(parent);
        message.setType(MessageType.FORWARD);
        message.setDialog(dialog);
        if (request.getText() != null && !request.getText().isEmpty()){
            message.setText(request.getText());
        }
        return messageRepository.saveAndFlush(message);
    }

}