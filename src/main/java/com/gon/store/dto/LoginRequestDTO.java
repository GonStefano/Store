package com.gon.store.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
    @NotBlank(message = "Username is obligatory")
    String username,
    @NotBlank(message = "Password is obligatory")
    String password
) {}
