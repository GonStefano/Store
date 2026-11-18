package com.gon.store.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SaleRequestDTO(
    @NotNull(message = "Customer is obligatory")
    Long customerId,

    @NotEmpty(message = "The list of video games cannot be empty")
    List<Long> videoGamesId
) {}
