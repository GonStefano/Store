package com.gon.store.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public record Product(@Id Long id, String title, String platform, String category, BigDecimal price, Integer stock, LocalDate releaseDate) {
}
