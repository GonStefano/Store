package com.gon.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gon.store.dto.RegisterRequestDTO;
import com.gon.store.dto.UserResponseDTO;
import com.gon.store.entity.User;
import com.gon.store.service.UserService;

import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody RegisterRequestDTO user, UriComponentsBuilder url) {
        User newUser = userService.registerUser(user.username(), user.password(), user.rol());

        URI locationOfNewUser = url
            .path("/api/auth/{id}")
            .buildAndExpand(newUser.getId())
            .toUri();
        
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(
            new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRol()
            )
        );
    }
    
    
}
