package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gon.store.dto.AdminUserCreateRequestDTO;
import com.gon.store.dto.UserResponseDTO;
import com.gon.store.entity.User;
import com.gon.store.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }
    
    @Operation (summary = "Create user with assigned role (Admin only).", description = "Allows administrators to create users specifying their authority role.")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody AdminUserCreateRequestDTO request) {
        User newUser = userService.registerUser(request.username(), request.password(), request.rol());
        
        return ResponseEntity.ok(new UserResponseDTO(newUser.getId(), newUser.getUsername(), newUser.getRol()));
    }
    
}
