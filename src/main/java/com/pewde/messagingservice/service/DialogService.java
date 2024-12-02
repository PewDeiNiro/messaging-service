package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.enums.DialogType;
import com.pewde.messagingservice.exception.*;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.MessageRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.CreateDialogRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DialogService {

    @Autowired
    private DialogRepository dialogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Dialog getDialogById(int id){
        return dialogRepository.findById(id).orElseThrow(DialogDoesNotExistsException::new);
    }

    public List<Dialog> getAllDialogs(){
        return dialogRepository.findAll();
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
        if (request.getSenderId() == request.getReceiverId()) {
            throw new CanNotMessagingWithSelfException();
        }
        User sender = userRepository.findById(request.getSenderId()).orElseThrow(UserDoesNotExistsException::new),
                receiver = userRepository.findById(request.getReceiverId()).orElseThrow(UserDoesNotExistsException::new);
        //        AuthService.checkAuth(sender, token);
        if (receiver.getBlocklist().contains(sender)){
            throw new UserBlockedThisException();
        }
        if (!dialogRepository.findByCreatorAndType(sender, DialogType.DIALOG).isEmpty()
                || !dialogRepository.findByCreatorAndType(receiver, DialogType.DIALOG).isEmpty()) {
            throw new DialogAlreadyExistsException();
        }
        List<User> receivers = new ArrayList<>(List.of(sender, receiver));
        List<Message> messages = new ArrayList<>();
        Dialog dialog = new Dialog();
        dialog.setCreator(sender);
        dialog.setCollocutors(receivers);
        dialog.setTitle("");
        dialog.setType(DialogType.DIALOG);
        if (request.getMessage() != null && !request.getMessage().isEmpty()) {
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
