package com.gon.store.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.gon.store.model.Product;
import java.util.List;


public interface ProductRepository extends ListCrudRepository<Product, Long> {
    List<Product> findByPlatform(String platform);
}
