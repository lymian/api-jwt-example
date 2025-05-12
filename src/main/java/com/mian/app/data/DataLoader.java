package com.mian.app.data;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mian.app.entities.Rol;
import com.mian.app.entities.Usuario;
import com.mian.app.entities.enums.RolEnum;
import com.mian.app.repositories.IRolRepository;
import com.mian.app.repositories.IUsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(
            IUsuarioRepository userRepository,
            IRolRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                System.out.println("Creating roles...");
                Rol adminRole = new Rol();
                adminRole.setNombre(RolEnum.ROLE_ADMIN);

                Rol userRole = new Rol();
                userRole.setNombre(RolEnum.ROLE_USER);

                roleRepository.saveAll(List.of(adminRole, userRole));

                System.out.println("Creating users...");
                Usuario adminUser = new Usuario();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("123456"));
                adminUser.setRol(adminRole);
                Usuario normalUser = new Usuario();
                normalUser.setUsername("user");
                normalUser.setPassword(passwordEncoder.encode("123456"));
                normalUser.setRol(userRole);
                userRepository.saveAll(List.of(adminUser, normalUser));
                System.out.println("Users created successfully");
            } else {
                System.out.println("Users already exist");
            }

        };
    }
}