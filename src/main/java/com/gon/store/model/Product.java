package com.gon.store.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

public record Product(@Id Long id, String name, String category, BigDecimal price, Integer stock) {

}
