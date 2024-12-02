package com.pewde.messagingservice.exception;

public class UserAlreadyUnblockedException extends BadRequestException{

    public UserAlreadyUnblockedException() {
        super("Пользователь не находится в блокировке");
    }

}
