package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.model.Product;
import com.gon.store.repository.ProductRepository;
import com.gon.store.service.ProductService;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/juegos")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    private ProductController(ProductRepository productRepository, ProductService productService){
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product != null) {
            return ResponseEntity.ok(product.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Product product, UriComponentsBuilder ucb) {
        Product producto = productService.createProduct(product);
        URI locationOfNewProduct = ucb
            .path("juegos/{id}")
            .buildAndExpand(producto.id())
            .toUri();
        return ResponseEntity.created(locationOfNewProduct).build();
    }
    
    
}
