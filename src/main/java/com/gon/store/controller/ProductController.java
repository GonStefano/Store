package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.model.Product;
import com.gon.store.service.ProductService;

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
public class ProductController {
    private final ProductService productService;

    private ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product, UriComponentsBuilder ucb) {
        Product producto = productService.createProduct(product);
        URI locationOfNewProduct = ucb
            .path("/api/videojuegos/{id}")
            .buildAndExpand(producto.id())
            .toUri();
        return ResponseEntity.created(locationOfNewProduct).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody Product product) {
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
    public ResponseEntity<List<Product>> getPlatform(@PathVariable String platform) {
        List<Product> products = productService.ProductByPlatform(platform);
        if (products == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(products);
        }
    }
    
    
}
