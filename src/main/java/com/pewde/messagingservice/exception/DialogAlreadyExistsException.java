package com.pewde.messagingservice.exception;

public class DialogAlreadyExistsException extends BadRequestException{

    public DialogAlreadyExistsException() {
        super("Диалог с этим пользователем уже существует");
    }

}
