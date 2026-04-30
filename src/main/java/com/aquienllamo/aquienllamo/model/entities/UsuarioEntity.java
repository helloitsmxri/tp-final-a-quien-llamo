package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(nullable = false, length = 50)
    private String tipo_imagen;

    @Lob
    private byte[] foto;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String clave;

    @Column(nullable = false, length = 50)
    private String telefono;

    @Column(nullable = false)
    private LocalDate fecha_registro;

    @Column(name = "ultima_actividad")
    private LocalDateTime ultima_actividad;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sobre_mi;
    
}
