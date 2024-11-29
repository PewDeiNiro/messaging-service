package com.pewde.messagingservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpException extends RuntimeException{

    private int code;

    private HttpStatus status;

    public HttpException(String message) {
        super(message);
    }

}
