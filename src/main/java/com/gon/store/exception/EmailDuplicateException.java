package com.gon.store.exception;

public class EmailDuplicateException extends RuntimeException{
    public EmailDuplicateException(){
        super("Registration could not be completed.");
    }
}
