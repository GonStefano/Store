package com.gon.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gon.store.dto.AuthResponseDTO;
import com.gon.store.dto.LoginRequestDTO;
import com.gon.store.dto.RegisterRequestDTO;
import com.gon.store.dto.UserResponseDTO;
import com.gon.store.entity.Rol;
import com.gon.store.entity.User;
import com.gon.store.service.JwtService;
import com.gon.store.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Register a new user.", description = "Registers a new user into the database.")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register( @Valid @RequestBody RegisterRequestDTO request) {
        User user = userService.registerUser(request.username(), request.password());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    @Operation(summary = "Authenticate user and get JWT token.", description = "Authenticates credentials and returns a signed JWT token.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails userDetails = userService.loadUserByUsername(request.username());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    
    @Operation(summary = "Get current user profile.", description = "Retrieves the authenticated user's profile information.")
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
