package com.gon.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gon.store.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
}
