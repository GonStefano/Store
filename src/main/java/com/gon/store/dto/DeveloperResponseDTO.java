package com.gon.store.dto;

import java.util.List;

public record DeveloperResponseDTO(
    Long id,
    String name,
    String country,
    String website,
    List<String> gameTitles
) {}
