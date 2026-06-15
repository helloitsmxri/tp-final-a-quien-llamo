package com.aquienllamo.aquienllamo.model.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileStorageService {
    private final String uploadDir = "uploads/chat/";

    public String guardarArchivo(MultipartFile archivo) throws IOException {
        String contentType = archivo.getContentType();
        if (contentType == null ||
                (!contentType.startsWith("image/") && !contentType.equals("application/pdf"))) {
            throw new IllegalArgumentException("Solo se permiten imágenes y PDFs");
        }

        Path dirPath = Paths.get(uploadDir);
        Files.createDirectories(dirPath);

        // nombre único para evitar colisiones
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        Path rutaCompleta = dirPath.resolve(nombreArchivo);

        Files.copy(archivo.getInputStream(), rutaCompleta, StandardCopyOption.REPLACE_EXISTING);

        return uploadDir + nombreArchivo; // guardás esta ruta en BD
    }
}
