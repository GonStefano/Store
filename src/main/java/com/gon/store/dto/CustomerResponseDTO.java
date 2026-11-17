package com.gon.store.dto;

import java.time.LocalDate;

public record CustomerResponseDTO(
    Long id,
    String name,
    String email,
    String phoneNumber,
    LocalDate registrationDate
) {}
