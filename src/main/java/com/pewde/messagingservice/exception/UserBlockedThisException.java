package com.pewde.messagingservice.exception;

public class UserBlockedThisException extends BadRequestException{

    public UserBlockedThisException() {
        super("Вы не можете выполнить данную операцию - пользователь вас заблокировал");
    }

}
