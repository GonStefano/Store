package com.gon.store.dto;

import com.gon.store.entity.Rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
    @NotBlank(message = "Username is obligatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username,

    @NotBlank(message = "Password is obligatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password,

    @NotNull(message = "Rol is obligatory")
    Rol rol
) {}
