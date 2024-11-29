package com.pewde.messagingservice.exception;

public class InvalidTokenException extends UnauthorizedException{

    public InvalidTokenException() {
        super("Невалидный токен пользователя");
    }

}
