package com.gon.store.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record SaleResponseDTO(
    Long id,
    LocalDateTime saleDate,
    String customerName,
    List<String> videoGameTitles,
    BigDecimal total 
) {

}
