package com.gon.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long>{

}
