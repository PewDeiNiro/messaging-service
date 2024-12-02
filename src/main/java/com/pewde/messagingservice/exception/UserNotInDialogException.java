package com.pewde.messagingservice.exception;

public class UserNotInDialogException extends BadRequestException{

    public UserNotInDialogException() {
        super("Пользователя нет в списках участников диалога");
    }

}
