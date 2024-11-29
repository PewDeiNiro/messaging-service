package com.pewde.messagingservice.exception;

public class DialogDoesNotExistsException extends BadRequestException{

    public DialogDoesNotExistsException() {
        super("Дилога с таким уникальным идентификатором не существует");
    }

}
