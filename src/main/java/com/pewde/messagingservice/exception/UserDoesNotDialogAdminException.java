package com.pewde.messagingservice.exception;

public class UserDoesNotDialogAdminException extends BadRequestException{

    public UserDoesNotDialogAdminException() {
        super("Невозможно выполнить операцию - пользователь не является администратором беседы");
    }

}
