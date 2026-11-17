package com.gon.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeveloperRequestDTO(
    @NotBlank(message = "Nombre es obligatorio")
    @Size(max = 255, message = "Nombre no debe superar los 255 caracteres")
    String name,
    @NotBlank(message = "Pais es obligatorio")
    @Size(max = 255, message = "Pais no debe superar los 255 caracteres")
    String country,
    String website
) {}
