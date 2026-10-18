package com.gon.store.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gon.store.model.Product;
import com.gon.store.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El precio debe ser mayor a 0");
        }
        if (product.stock() == null || product.stock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El Stock no debe ser negativo");
        }

        return productRepository.save(product);
    }

    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public Iterable<Product> findAll(){
        return productRepository.findAll();
    } 
}
