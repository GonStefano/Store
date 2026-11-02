package com.gon.store.exception;

public class VideoGameNotFoundException extends RuntimeException{
    public VideoGameNotFoundException(Long id) {
        super("Videojuego con ID " + id + " no encontrado");
    }

}
