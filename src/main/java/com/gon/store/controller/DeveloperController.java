package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.DeveloperRequestDTO;
import com.gon.store.dto.DeveloperResponseDTO;
import com.gon.store.service.DeveloperService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService){
        this.developerService = developerService;
    }

    @PostMapping
    public ResponseEntity<Void> createDeveloper(@Valid @RequestBody DeveloperRequestDTO developer, UriComponentsBuilder ucb) {
        DeveloperResponseDTO dev = developerService.createDeveloper(developer);
        URI locationOfNewDeveloper = ucb
            .path("/api/developer/{id}")
            .buildAndExpand(dev.id())
            .toUri();

        return ResponseEntity.created(locationOfNewDeveloper).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(developerService.findById(id));
    }
    

    @GetMapping
    public ResponseEntity<List<DeveloperResponseDTO>> findAll() {
        return ResponseEntity.ok(developerService.findAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDeveloper(@PathVariable Long id, @Valid  @RequestBody DeveloperRequestDTO developer) {
        developerService.updateDeveloper(id, developer);
        
        return ResponseEntity.noContent().build();
    }
}
