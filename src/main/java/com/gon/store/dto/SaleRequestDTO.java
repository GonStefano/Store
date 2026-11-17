package com.gon.store.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record SaleRequestDTO(
    @NotNull(message = "Customer is obligatory")
    Long customerId,

    List<Long> videoGamesId
) {}
