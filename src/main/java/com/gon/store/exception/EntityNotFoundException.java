package com.gon.store.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entity,Long id){
        super(entity+" with ID "+id+" not found");
    }
}
