package com.pewde.messagingservice.exception;

public class TokenExpiredException extends UnauthorizedException{

    public TokenExpiredException() {
        super("Срок действия токена истек");
    }

}
