package com.aquienllamo.aquienllamo.model.auth;

import com.aquienllamo.aquienllamo.model.auth.JWT.JwtAuthenticationFilter;
import com.aquienllamo.aquienllamo.model.auth.exceptions.RestAuthenticateEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean     //Bean "encriptador"
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, RestAuthenticateEntryPoint restAuthenticationEntryPoint) throws Exception {
       http.authorizeHttpRequests(auth->
               auth
                       .requestMatchers(
                               "/auth/**",
                               "/aquienllamo/usuarios/sign-up",
                               "/aquienllamo/usuarios/login",
                               "/aquienllamo/administradores/registrar",
                               "/aquienllamo/administradores/login"
                       ).permitAll()
                       .requestMatchers("/aquienllamo/usuarios/todos").hasRole("ADMIN")
                       .requestMatchers("/admin/**").hasRole("ADMIN")  // protege todo lo del admin
                       .requestMatchers("/chats/**").authenticated()   // solo usuarios autenticados
                       .requestMatchers("/mensajes/**").authenticated() // solo usuarios autenticados

                       .requestMatchers(HttpMethod.POST, "/aquienllamo/pagos").hasRole("CLIENTE")
                       .requestMatchers(HttpMethod.PATCH, "/aquienllamo/pagos/*/cancelar").hasRole("CLIENTE")
                       .requestMatchers("/aquienllamo/pagos/cliente/**").hasAnyRole("CLIENTE", "ADMIN")
                       .requestMatchers("/aquienllamo/pagos/tecnico/**").hasAnyRole("TECNICO", "ADMIN") //esto no estoy segura si es tecnico o prestador

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