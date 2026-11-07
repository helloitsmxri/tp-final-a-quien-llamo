package com.aquienllamo.aquienllamo.entidades.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Misma carpeta donde FileStorageService guarda los archivos ("uploads/")
        Path uploads = Paths.get("uploads").toAbsolutePath().normalize();

        String ubicacion = uploads.toUri().toString();
        if (!ubicacion.endsWith("/")) {
            ubicacion += "/"; // Spring necesita la barra final para tratarlo como carpeta
        }

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(ubicacion);
    }
}
