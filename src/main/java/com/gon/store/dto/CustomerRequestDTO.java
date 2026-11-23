package com.gon.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequestDTO(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Email is required")
    @Email(message = "The email format is not valid (e.g., user@domain.com)")
    String email,
    @NotBlank(message = "Phone number is required")
    String phoneNumber
) {}
