package com.gon.store.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gon.store.entity.Rol;
import com.gon.store.entity.User;
import com.gon.store.exception.RegisterUserException;
import com.gon.store.repository.UserRepository;

@Service 
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String rawPassword, Rol rol){
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RegisterUserException(username);
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User(username, encodedPassword, rol);
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

}
