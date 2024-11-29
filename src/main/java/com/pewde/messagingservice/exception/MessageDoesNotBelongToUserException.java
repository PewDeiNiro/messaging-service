package com.pewde.messagingservice.exception;

public class MessageDoesNotBelongToUserException extends BadRequestException{

    public MessageDoesNotBelongToUserException() {
        super("Пользователь не является автором сообщения");
    }

}
