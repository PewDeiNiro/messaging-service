package com.pewde.messagingservice.exception;

public class MessageDoesNotExistsException extends BadRequestException{

    public MessageDoesNotExistsException() {
        super("Сообщения с таким уникальным идентификатором не существует");
    }

}
