package com.gon.store.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceBetweenRequestDTO(

    @NotNull(message = "Minimum price is required")
    @Positive (message = "Must be positive")
    BigDecimal minPrice,

   @NotNull(message = "Maximum price is required")
   @Positive(message = "Must be positive")
    BigDecimal maxPrice
) {}
