package com.pewde.messagingservice.exception;

public class CanNotMessagingWithSelfException extends BadRequestException {

    public CanNotMessagingWithSelfException() {
        super("Нельзя написать самому себе");
    }

}
