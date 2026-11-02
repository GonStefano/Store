package com.gon.store.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceBetweenRequestDTO(

    @NotNull  (message = "El precio minimo es obligatorio")
    @Positive (message = "Debe ser positivo")
    BigDecimal minPrice,

   @NotNull  (message = "El precio maximo es obligatorio")
   @Positive(message = "Debe ser positivo")
    BigDecimal maxPrice
) {}
