package com.gon.store.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record VideoGameRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 255, message = "El título no puede superar los 255 caracteres")
    String title,

    @NotBlank(message = "La plataforma es obligatoria")
    @Size(max = 255, message = "La plataforma no puede superar los 255 caracteres")
    String platform,

    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 255, message = "La categoría no puede superar los 255 caracteres")
    String category,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    BigDecimal price,

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no debe ser negativo")
    Integer stock,

    @NotNull(message = "La fecha de lanzamiento es obligatoria")
    LocalDate releaseDate
) {}
