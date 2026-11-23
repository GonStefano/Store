package com.gon.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeveloperRequestDTO(
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name,
    @NotBlank(message = "Country is required")
    @Size(max = 255, message = "Country must not exceed 255 characters")
    String country,
    String website
) {}
