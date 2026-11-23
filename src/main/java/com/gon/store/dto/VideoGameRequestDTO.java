package com.gon.store.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VideoGameRequestDTO(
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    String title,

    @NotBlank(message = "Platform is required")
    @Size(max = 255, message = "Platform must not exceed 255 characters")
    String platform,

    @NotBlank(message = "Category is required")
    @Size(max = 255, message = "Category must not exceed 255 characters")
    String category,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    BigDecimal price,

    @NotNull(message = "Stock is required")
    @PositiveOrZero(message = "Stock must not be negative")
    Integer stock,

    @NotNull(message = "Release date is required")
    LocalDate releaseDate,

    @NotNull(message = "Developer ID is required")
    Long developerId
) {}
