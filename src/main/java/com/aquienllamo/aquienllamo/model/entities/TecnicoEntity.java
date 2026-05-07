package com.aquienllamo.aquienllamo.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

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

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @PrePersist
    public void generarUUID() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

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
