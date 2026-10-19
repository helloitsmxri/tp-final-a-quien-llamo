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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean     //Bean "encriptador"
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter,
            RestAuthenticateEntryPoint restAuthenticationEntryPoint
    ) throws Exception {

        http
                .cors(Customizer.withDefaults())

                .csrf(AbstractHttpConfigurer::disable)

                .headers(headers ->
                        headers.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .sessionManagement(manager ->
                        manager.sessionCreationPolicy(STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // Permitir las peticiones previas de CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Rutas públicas
                        .requestMatchers(
                                "/auth/**",
                                "/aquienllamo/usuarios/sign-up",
                                "/aquienllamo/usuarios/login",
                                "/aquienllamo/administradores/registrar",
                                "/aquienllamo/tecnicos/registrar-nuevo"
                        ).permitAll()

                        // Rutas protegidas
                        .requestMatchers("/aquienllamo/usuarios/todos")
                        .hasRole("ADMINISTRADOR")

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/chats/**").authenticated()
                        .requestMatchers("/mensajes/**").authenticated()

                        .requestMatchers(HttpMethod.POST, "/aquienllamo/pagos")
                        .hasRole("USUARIO")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/aquienllamo/pagos/*/cancelar"
                        ).hasRole("USUARIO")

                        .requestMatchers("/aquienllamo/pagos/cliente/**")
                        .hasAnyRole("USUARIO", "ADMINISTRADOR")

                        .requestMatchers("/aquienllamo/pagos/tecnico/**")
                        .hasAnyRole("TECNICO", "ADMINISTRADOR")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .exceptionHandling(e ->
                        e.authenticationEntryPoint(restAuthenticationEntryPoint)
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir peticiones desde Angular
        configuration.setAllowedOrigins(
                List.of("http://localhost:4200")
        );

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        // Cabeceras permitidas
        configuration.setAllowedHeaders(
                List.of("*")
        );

        // Permitir cabeceras de autorización, como Bearer JWT
        configuration.setExposedHeaders(
                List.of("Authorization")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        // Aplicar la configuración a todos los endpoints
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


}