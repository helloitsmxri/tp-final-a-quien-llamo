package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") // Esto vincula el nombre de la DB con la variable de java
    private Integer idUsuario;

    @Column(name = "tipo_imagen", nullable = false, length = 50)
    private String tipoImagen;

    @Lob
    @Column(name = "foto", columnDefinition = "MEDIUMBLOB")
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

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_registro", nullable = false, insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actividad", nullable = false, updatable = true)
    private LocalDateTime ultimaActividad;

    @Column(name = "sobre_mi", nullable = false, columnDefinition = "TEXT")
    private String sobreMi;

    // Relación @OneToOne con Técnico
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private TecnicoEntity tecnico;

    // Relación @OneToMany con Ubicación
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UbicacionEntity>ubicaciones;

    // Relación @OneToMany con Rating, un usuario puede ENVIAR una calificación y recibirla.
    @OneToMany(mappedBy = "usuarioRemitente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingEntity>calificacionesEnviadas;

    @OneToMany(mappedBy = "usuarioDestinatario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RatingEntity>calificacionesRecibidas;

    // Relación @OneToMany con Presupuesto
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PresupuestoEntity>presupuestosSolicitados;
    
}
