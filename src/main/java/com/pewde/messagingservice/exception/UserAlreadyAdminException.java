package com.pewde.messagingservice.exception;

public class UserAlreadyAdminException extends BadRequestException{

    public UserAlreadyAdminException() {
        super("Пользователь уже является администратором");
    }

}
