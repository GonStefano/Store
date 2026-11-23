package com.gon.store.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gon.store.entity.Rol;
import com.gon.store.entity.User;
import com.gon.store.repository.UserRepository;

@Component 
public class DataInitializer implements CommandLineRunner{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                "admin", 
                passwordEncoder.encode("admin123"), 
                Rol.ADMIN
            );

            userRepository.save(admin);
            System.out.println(">>> Initial admin user created: admin / admin123");
        }
    }


}
