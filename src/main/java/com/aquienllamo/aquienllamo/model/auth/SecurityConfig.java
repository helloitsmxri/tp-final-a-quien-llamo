package com.aquienllamo.aquienllamo.model.auth;

import com.aquienllamo.aquienllamo.model.auth.JWT.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
public class SecurityConfig {

    @Bean     //Bean "encriptador"
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, RestAuthenticationEntryPoint restAuthenticationEntryPoint) throws Exception {
       http.authorizeHttpRequests(auth->
               auth.requestMatchers("/auth/**").permitAll()
                       .anyRequest().authenticated())
               .cors(Customizer.withDefaults())
               .csrf(AbstractHttpConfigurer::disable)
               .headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
               .sessionManagement(manager-> manager.sessionCreationPolicy(STATELESS))
               .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling(e-> e.authenticationEntryPoint(restAuthenticationEntryPoint));
       return http.build();
    }

    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }


}