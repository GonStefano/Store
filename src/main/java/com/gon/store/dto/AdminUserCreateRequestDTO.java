package com.gon.store.dto;

import com.gon.store.entity.Rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminUserCreateRequestDTO(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 6)
    String password,

    @NotNull(message = "Role is required")
    Rol rol
) {}
