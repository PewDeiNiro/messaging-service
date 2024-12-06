package com.pewde.messagingservice.service;

import com.pewde.messagingservice.entity.Dialog;
import com.pewde.messagingservice.entity.Message;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.enums.DialogType;
import com.pewde.messagingservice.enums.MessageType;
import com.pewde.messagingservice.exception.*;
import com.pewde.messagingservice.repository.DialogRepository;
import com.pewde.messagingservice.repository.UserRepository;
import com.pewde.messagingservice.request.CreateConversationRequest;
import com.pewde.messagingservice.request.KickOrInviteRequest;
import com.pewde.messagingservice.request.LeaveConversationRequest;
import com.pewde.messagingservice.request.MakeOrUnmakeAdminRequest;
import com.pewde.messagingservice.token.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        User sender = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(sender, token);
        List<User> receivers = new ArrayList<>(List.of(sender));
        for (int id : request.getTargetIds()){
            User receiver = userRepository.findById(id).orElseThrow(UserDoesNotExistsException::new);
            if (receiver.getBlocklist().contains(sender)){
                throw new UserBlockedThisException();
            }
            if (sender.getId() != id) {
                receivers.add(receiver);
            }
        }
        List<User> collocutors = new ArrayList<>(List.copyOf(receivers)), admins = new ArrayList<>(List.of(sender));
        List<Message> messages = new ArrayList<>();
        Dialog dialog = new Dialog();
        dialog.setCollocutors(collocutors);
        dialog.setCreator(sender);
        dialog.setAdmins(admins);
        dialog.setType(DialogType.CONVERSATION);
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
            message.setType(MessageType.MESSAGE);
            messages.add(message);
        }
        dialog.setMessages(messages);
        return dialogRepository.saveAndFlush(dialog);
    }

    public ResponseEntity<HttpStatus> leaveConversation(LeaveConversationRequest request, String token){
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
        if (dialog.getType() == DialogType.DIALOG){
            throw new OperationNotForDialogException();
        }
        User user = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new);
//        AuthService.checkAuth(user, token);
        if (!dialog.getCollocutors().contains(user)){
            throw new UserDoesNotBelongToDialogException();
        }
        dialog.getCollocutors().remove(user);
        dialogRepository.saveAndFlush(dialog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Dialog inviteUser(KickOrInviteRequest request, String token){
        User admin = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new),
                user = userRepository.findById(request.getTargetId()).orElseThrow(UserDoesNotExistsException::new);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
//        AuthService.checkAuth(admin, token);
        if (dialog.getType() == DialogType.DIALOG){
            throw new OperationNotForDialogException();
        }
        if (user.getBlocklist().contains(admin)){
            throw new UserBlockedThisException();
        }
        if (!dialog.getAdmins().contains(admin)){
            throw new UserDoesNotDialogAdminException();
        }
        if (dialog.getCollocutors().contains(user)){
            throw new UserAlreadyInDialogException();
        }
        dialog.getCollocutors().add(user);
        return dialogRepository.saveAndFlush(dialog);
    }

    public ResponseEntity<HttpStatus> kickUser(KickOrInviteRequest request, String token){
        User admin = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new),
                user = userRepository.findById(request.getTargetId()).orElseThrow(UserDoesNotExistsException::new);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
//        AuthService.checkAuth(admin, token);
        if (dialog.getType() == DialogType.DIALOG){
            throw new OperationNotForDialogException();
        }
        if (!dialog.getAdmins().contains(admin)){
            throw new UserDoesNotDialogAdminException();
        }
        if (!dialog.getCreator().equals(admin) && dialog.getAdmins().contains(user)){
            throw new CanNotKickAdminException();
        }
        if (!dialog.getCollocutors().contains(user)){
            throw new UserNotInDialogException();
        }
        dialog.getCollocutors().remove(user);
        dialog.getAdmins().remove(user);
        dialogRepository.saveAndFlush(dialog);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Dialog appointAdmin(MakeOrUnmakeAdminRequest request, String token){
        User creator = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new),
                user = userRepository.findById(request.getTargetId()).orElseThrow(UserDoesNotExistsException::new);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
//        AuthService.checkAuth(creator, token);
        if (dialog.getType() == DialogType.DIALOG){
            throw new OperationNotForDialogException();
        }
        if (!dialog.getCreator().equals(creator)){
            throw new UserDoesNotCreatorException();
        }
        if (!dialog.getCollocutors().contains(user)){
            throw new UserNotInDialogException();
        }
        if (dialog.getAdmins().contains(user)){
            throw new UserAlreadyAdminException();
        }
        dialog.getAdmins().add(user);
        return dialogRepository.saveAndFlush(dialog);
    }

    public Dialog demoteAdmin(MakeOrUnmakeAdminRequest request, String token){
        User creator = userRepository.findById(request.getUserId()).orElseThrow(UserDoesNotExistsException::new),
                user = userRepository.findById(request.getTargetId()).orElseThrow(UserDoesNotExistsException::new);
        Dialog dialog = dialogRepository.findById(request.getDialogId()).orElseThrow(DialogDoesNotExistsException::new);
//        AuthService.checkAuth(creator, token);
        if (dialog.getType() == DialogType.DIALOG){
            throw new OperationNotForDialogException();
        }
        if (!dialog.getCreator().equals(creator)){
            throw new UserDoesNotCreatorException();
        }
        if (!dialog.getCollocutors().contains(user)){
            throw new UserNotInDialogException();
        }
        if (!dialog.getAdmins().contains(user)){
            throw new UserDoesNotDialogAdminException();
        }
        dialog.getAdmins().remove(user);
        return dialogRepository.saveAndFlush(dialog);
    }

}
