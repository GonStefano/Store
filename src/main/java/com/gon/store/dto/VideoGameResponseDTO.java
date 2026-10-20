package com.gon.store.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VideoGameResponseDTO(
    Long id,
    String title,
    String platform,
    String category,
    BigDecimal price,
    Integer stock,
    LocalDate releaseDate
) {}
