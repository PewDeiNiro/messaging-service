package com.pewde.messagingservice.exception;

public class UserDoesNotBelongToDialogException extends BadRequestException{

    public UserDoesNotBelongToDialogException() {
        super("Пользователь не находится в этом диалоге");
    }

}
