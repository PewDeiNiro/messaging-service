package com.pewde.messagingservice.exception;

public class CanNotKickAdminException extends BadRequestException{

    public CanNotKickAdminException() {
        super("Вы не можете исключить администратора");
    }

}
