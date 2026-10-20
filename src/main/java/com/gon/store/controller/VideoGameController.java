package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.VideoGameRequestDTO;
import com.gon.store.dto.VideoGameResponseDTO;
import com.gon.store.service.VideoGameService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/videojuegos")
public class VideoGameController {
    private final VideoGameService productService;

    private VideoGameController(VideoGameService productService){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGameResponseDTO> findById(@PathVariable Long id) {
        Optional<VideoGameResponseDTO> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VideoGameResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@Valid @RequestBody VideoGameRequestDTO product, UriComponentsBuilder ucb) {
        VideoGameResponseDTO producto = productService.createProduct(product);
        URI locationOfNewProduct = ucb
            .path("/api/videojuegos/{id}")
            .buildAndExpand(producto.id())
            .toUri();
        return ResponseEntity.created(locationOfNewProduct).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody VideoGameRequestDTO product) {
        boolean update = productService.updateProduct(id, product);
        
        if (update) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean delete = productService.deleteProduct(id);
        
        if (delete) {
            return ResponseEntity.noContent().build();
        }
            
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<List<VideoGameResponseDTO>> getPlatform(@PathVariable String platform) {
        List<VideoGameResponseDTO> products = productService.ProductByPlatform(platform);
        if (products == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(products);
        }
    }
    
    
}
