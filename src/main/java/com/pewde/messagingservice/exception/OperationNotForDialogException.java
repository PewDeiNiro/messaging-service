package com.pewde.messagingservice.exception;

public class OperationNotForDialogException extends BadRequestException{

    public OperationNotForDialogException() {
        super("Невозможная операция для диалога");
    }

}
