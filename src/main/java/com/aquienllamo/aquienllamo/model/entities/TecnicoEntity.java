package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Column(name = "id_tecnico")
    private Integer idTecnico;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;

    @Column(nullable = false, length = 20)
    private String cuit;

    @Column(name = "descripcion_trabajo", columnDefinition = "TEXT")
    private String descripcionTrabajo;

    @Column(columnDefinition = "TEXT")
    private String proyectos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Habilidad_Tecnico",
            joinColumns = @JoinColumn(name = "id_tecnico"),
            inverseJoinColumns = @JoinColumn(name = "id_habilidad")

    )
    private List<HabilidadEntity> habilidades;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Especialidad_Tecnico",
            joinColumns = @JoinColumn(name = "id_tecnico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    private List<EspecialidadEntity> especialidades;

}
