package com.pewde.messagingservice.exception;

public class UserAlreadyInDialogException extends BadRequestException{

    public UserAlreadyInDialogException() {
        super("Пользователь уже состоит в диалоге");
    }

}
