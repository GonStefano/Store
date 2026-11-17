package com.gon.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long>{
    List<Sale> findByCustomerId(Long customerId);
    

}
