package com.gon.store.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entity,Long id){
        super(entity+" con ID "+id+" no encontrado");
    }
}
