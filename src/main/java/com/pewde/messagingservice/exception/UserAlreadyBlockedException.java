package com.pewde.messagingservice.exception;

public class UserAlreadyBlockedException extends BadRequestException{

    public UserAlreadyBlockedException() {
        super("Пользователь уже заблокирован");
    }

}
