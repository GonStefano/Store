package com.gon.store.repository;

import org.springframework.data.repository.CrudRepository;

import com.gon.store.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

}
