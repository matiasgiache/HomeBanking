package com.TP.TP.exceptions;

public class UserNotExistsException extends RuntimeException{

    public UserNotExistsException(String message){
        super(message);
    }
}

