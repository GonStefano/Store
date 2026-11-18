package com.gon.store.dto;

import com.gon.store.entity.Rol;

public record UserResponseDTO(
    Long id,
    String username,
    Rol rol
) {}
