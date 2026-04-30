package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name="Tecnico")
public class TecnicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_tecnico;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity id_usuario;

    @Column(nullable = false, length = 20)
    private String cuit;

    @Column(columnDefinition = "TEXT")
    private String descripcion_trabajo;

    @Column(columnDefinition = "TEXT")
    private String proyectos;
}
