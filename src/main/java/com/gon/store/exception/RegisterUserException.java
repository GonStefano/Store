package com.gon.store.exception;

public class RegisterUserException extends RuntimeException{
    public RegisterUserException(String username){
        super("The username "+username+" is already in use.");
    }

}
