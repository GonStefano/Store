package com.gon.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    boolean existsByEmail(String email);
}
