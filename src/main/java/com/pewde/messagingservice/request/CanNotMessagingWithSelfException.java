package com.pewde.messagingservice.request;

import com.pewde.messagingservice.exception.BadRequestException;

public class CanNotMessagingWithSelfException extends BadRequestException {

    public CanNotMessagingWithSelfException() {
        super("Нельзя написать самому себе");
    }

}
