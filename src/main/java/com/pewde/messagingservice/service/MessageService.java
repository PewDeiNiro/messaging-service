package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.exception.*;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.MessageRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.CreateDialogRequest;
import com.pewde.messagingservice.request.DeleteMessageRequest;
import com.pewde.messagingservice.request.SendMessageRequest;
import com.pewde.messagingservice.token.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Dialog getDialogById(int id){
        return dialogRepository.findById(id).orElseThrow(DialogDoesNotExistsException::new);
    }

    public List<Dialog> getAllDialogs(){
        return dialogRepository.findAll();
    }

    public Message getMessageById(int id){
        return messageRepository.findById(id).orElseThrow(MessageDoesNotExistsException::new);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByDialog(int id){
        return messageRepository.findByDialogOrderById(dialogRepository.findById(id).orElseThrow(DialogDoesNotExistsException::new));
    }

    public Message getLastMessageInDialog(int id){
        return messageRepository.findTopByDialogOrderByIdDesc(dialogRepository.findById(id).orElseThrow(DialogDoesNotExistsException::new))
                .orElseThrow(MessageDoesNotExistsException::new);
    }

    public List<Message> getMessageAfterIdInDialog(int dialogId, int messageId){
        Dialog dialog = dialogRepository.findById(dialogId).orElseThrow(DialogDoesNotExistsException::new);
        List<Message> messages = dialog.getMessages();
        messages.removeIf(message -> message.getId() <= messageId);
        return messages;
    }

    public Dialog createDialog(CreateDialogRequest request, String token){
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        List<User> receivers = new ArrayList<>(List.of(sender));
        for (int id : request.getReceiverIds()){
            receivers.add(userRepository.findById(id).orElseThrow(UserDoesNotExistsException::new));
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

}
