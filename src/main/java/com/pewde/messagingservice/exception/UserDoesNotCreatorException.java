package com.pewde.messagingservice.exception;

public class UserDoesNotCreatorException extends BadRequestException{

    public UserDoesNotCreatorException(){
        super("Пользователь не является создателем беседы");
    }

}
