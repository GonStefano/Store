package com.gon.store.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.SaleRequestDTO;
import com.gon.store.dto.SaleResponseDTO;
import com.gon.store.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/sale")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }

    @Operation(summary = "Register a new sale.", description = "Processes and records a new video game sale for a customer.")
    @PostMapping
    public ResponseEntity<Void> createSale(@Valid @RequestBody SaleRequestDTO saleRequestDTO, UriComponentsBuilder uri) {
        SaleResponseDTO sale = saleService.createSale(saleRequestDTO);
        URI locationOfNewSale = uri
            .path("/api/sale/{id}")
            .buildAndExpand(sale.id())
            .toUri();
        return ResponseEntity.created(locationOfNewSale).build();
    }
    
}
