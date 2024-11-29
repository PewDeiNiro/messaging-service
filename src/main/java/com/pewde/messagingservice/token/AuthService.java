package com.pewde.messagingservice.token;

import com.pewde.messagingservice.entity.Token;
import com.pewde.messagingservice.entity.User;
import com.pewde.messagingservice.exception.InvalidTokenException;
import com.pewde.messagingservice.exception.TokenExpiredException;

import java.util.Date;

public class AuthService {

    public static void checkAuth(User user, String token){
        token = token.replaceAll("Bearer ", "");
        Token userToken = user.getToken();
        if (userToken == null || !token.equals(userToken.getToken())){
            throw new InvalidTokenException();
        }
        if (userToken.getExpiry().before(new Date())){
            throw new TokenExpiredException();
        }
    }

}
