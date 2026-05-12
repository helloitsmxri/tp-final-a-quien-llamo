package com.aquienllamo.aquienllamo.model.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean     //Bean "encriptador"
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // protección contra ataques web.
                // Le pongo disable porque sino Spring va a rechazar todos los Post, put y delete.
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // permito que se pueda acceder a todo sin autenticación.
                );
        return http.build();
    }

}