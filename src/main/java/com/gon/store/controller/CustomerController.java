package com.gon.store.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.CustomerRequestDTO;
import com.gon.store.dto.CustomerResponseDTO;
import com.gon.store.dto.SaleResponseDTO;
import com.gon.store.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;





@RestController 
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @Operation(summary = "Create a new customer.", description = "Registers a new customer in the system.")
    @PostMapping
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO, UriComponentsBuilder url) {
        CustomerResponseDTO customer = customerService.createCustomer(customerRequestDTO);

        URI locationOfNewCustomer = url
            .path("/api/customer/{id}")
            .buildAndExpand(customer.id())
            .toUri();

        return ResponseEntity.created(locationOfNewCustomer).build();
    }

    @Operation(summary = "Get customer by id.", description = "Retrieves customer details by their unique identifier.")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @Operation(summary = "Get all sales by customer id.", description = "Retrieves all sales history associated with a specific customer ID.")
    @GetMapping("/{id}/ventas")
    public ResponseEntity<List<SaleResponseDTO>> getSellByID(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findAllSales(id));
    }
    
    @Operation(summary = "Update a customer by id.", description = "Updates an existing customer's details by their ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @Valid  @RequestBody  CustomerRequestDTO customerRequestDTO) {
        customerService.updateCustomer(id, customerRequestDTO);
        
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Delete a customer by id.", description = "Removes a customer from the system by their ID.")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
    
}
